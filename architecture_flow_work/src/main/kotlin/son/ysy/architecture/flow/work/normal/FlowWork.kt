package son.ysy.architecture.flow.work.normal

import androidx.annotation.CheckResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import son.ysy.architecture.error.displayMessage
import son.ysy.architecture.flow.work.FlowTagUtil
import java.util.concurrent.atomic.AtomicReference

class FlowWork<T> internal constructor(
    internal val scope: CoroutineScope,
    private val initDataBlock: (() -> T)?,
    internal val cancelBeforeIfBusy: Boolean,
    internal val cancelCurrentIfBusy: Boolean,
    val busyFlow: MutableSharedFlow<Boolean?>?,
    val errorMessageFlow: MutableSharedFlow<String?>?,
    val dataFlow: MutableSharedFlow<T?>?,
    val statusFlow: MutableSharedFlow<FlowWorkStatus<T>?>?,
) : FlowWorkGetter<T> {
    companion object {

        @CheckResult
        inline fun <reified T> build(
            viewModel: ViewModel, builder: FlowWorkBuilder<T>.() -> Unit
        ): FlowWork<T> {
            return build(viewModel.viewModelScope, builder)
        }

        @CheckResult
        inline fun <reified T> build(
            scope: CoroutineScope,
            builder: FlowWorkBuilder<T>.() -> Unit
        ): FlowWork<T> {
            return FlowWorkBuilder<T>(scope).apply(builder).build()
        }

    }

    private val parentJobMap = mutableMapOf<String, Job>()

    internal fun getParentJob(tag: String): Job {
        return parentJobMap.getOrPut(tag) {
            SupervisorJob(scope.coroutineContext.job)
        }
    }

    private val data = AtomicReference<T>()

    internal fun getNewData(block: T?.() -> T?): T? {
        val oldData = data.get()

        return oldData?.block().apply(data::set)
    }

    private val sharedFlowLazy = lazy {
        MutableSharedFlow<FlowEmitValue<T>>(Int.MAX_VALUE)
    }

    internal val sharedFlow by sharedFlowLazy

    val currentData: T?
        get() = data.get()

    override val currentBusyFlow: Flow<Boolean>? by lazy {
        busyFlow?.map { it == true }
    }

    override val currentErrorMessageFlow: Flow<String>? by lazy {
        errorMessageFlow?.filterNotNull()
    }

    override val currentDataFlow: Flow<T?>? by lazy {
        dataFlow
    }

    override val currentStatusFlow: Flow<FlowWorkStatus<T>?>? by lazy {
        statusFlow
    }

    val getter: FlowWorkGetter<T> = this

    @ExperimentalCoroutinesApi
    internal fun bind() {
        if (!sharedFlowLazy.isInitialized()) {
            scope.launch(getParentJob(FlowTagUtil.getBindTag())) {
                if (busyFlow != null) {
                    sharedFlow.filterIsInstance<FlowEmitValue.Status<T>>()
                        .map { it.isBusy }
                        .onEach {
                            busyFlow.emit(it)
                        }.launchIn(this)
                }

                if (errorMessageFlow != null) {
                    sharedFlow.filterIsInstance<FlowEmitValue.Error<T>>()
                        .map { it.throwable.displayMessage }
                        .onEach {
                            errorMessageFlow.emit(it)
                        }.launchIn(this)
                }

                if (dataFlow != null) {
                    sharedFlow.filterIsInstance<FlowEmitValue.Data<T>>()
                        .onEach {
                            dataFlow.emit(it.data)
                        }
                        .launchIn(this)
                }

                if (statusFlow != null) {
                    sharedFlow.scan<FlowEmitValue<T>, FlowWorkStatus<T>?>(null) { old, new ->

                        when (new) {
                            is FlowEmitValue.Status.Start -> FlowWorkStatus.Busy(old?.data)
                            is FlowEmitValue.Status.Complete -> old?.copyNewBusy(false)
                            is FlowEmitValue.Data -> when (old) {
                                is FlowWorkStatus.Error -> FlowWorkStatus.Error(
                                    true,
                                    new.data,
                                    old.error,
                                    old.message
                                )
                                else -> FlowWorkStatus.Success(true, new.data)
                            }
                            is FlowEmitValue.Error -> FlowWorkStatus.Error(
                                true,
                                old?.data,
                                new.throwable
                            )
                        }
                    }.filterNotNull()
                        .onEach {
                            statusFlow.emit(it)
                        }.launchIn(this)
                }

                initDataBlock?.invoke()?.apply {
                    sharedFlow.tryEmit(FlowEmitValue.Data(this))
                }
            }
        }
    }

    suspend fun updateData(block: T?.() -> T?) {
        val newData = currentData?.block()?.apply(data::set)

        sharedFlow.emit(FlowEmitValue.Data(newData))
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun start(starter: FlowWorkStarter<T>.() -> Unit) {
        FlowWorkStarter(this).apply(starter)
            .start()
    }

    @Synchronized
    fun loadInitData(block: () -> Unit) {
        if (!sharedFlowLazy.isInitialized()) {
            block()
        }
    }
}