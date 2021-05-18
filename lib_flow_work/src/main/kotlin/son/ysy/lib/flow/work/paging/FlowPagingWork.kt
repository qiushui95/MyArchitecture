package son.ysy.lib.flow.work.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import son.ysy.lib.entity.PageInfo
import son.ysy.lib.error.displayMessage
import son.ysy.lib.flow.work.FlowTagUtil
import java.util.concurrent.atomic.AtomicReference

internal class FlowPagingWork<T : PagingUiInfo<T, L>, L> internal constructor(
    internal val scope: CoroutineScope,
    private val startPageInfo: PageInfo,
    initData: T,
    val errorMessageFlow: MutableSharedFlow<String?>?,
    val statusFlow: MutableSharedFlow<FlowPagingWorkStatus<T, L>?>?,
) : FlowPagingWorkGetter<T, L> {

    companion object {

        var defaultStartPageInfo: PageInfo? = null

        fun <T : PagingUiInfo<T, L>, L> build(
            viewModel: ViewModel,
            initData: T,
            builder: FlowPagingWorkBuilder<T, L>.() -> Unit
        ): FlowPagingWork<T, L> {
            return build(viewModel.viewModelScope, initData, builder)
        }

        fun <T : PagingUiInfo<T, L>, L> build(
            scope: CoroutineScope, initData: T, builder: FlowPagingWorkBuilder<T, L>.() -> Unit
        ): FlowPagingWork<T, L> {
            return FlowPagingWorkBuilder(scope, initData).apply(builder).build()
        }

        fun <T> buildList(
            viewModel: ViewModel,
            initData: PagingList<T> = PagingList.empty(),
            builder: FlowPagingWorkBuilder<PagingList<T>, T>.() -> Unit
        ): FlowPagingWork<PagingList<T>, T> {
            return build(viewModel.viewModelScope, initData, builder)
        }

        fun <T> buildList(
            scope: CoroutineScope,
            initData: PagingList<T> = PagingList.empty(),
            builder: FlowPagingWorkBuilder<PagingList<T>, T>.() -> Unit
        ): FlowPagingWork<PagingList<T>, T> {
            return build(scope, initData, builder)
        }
    }

    private val nextPageInfo = AtomicReference(startPageInfo)

    private val sharedFlowLazy = lazy {
        MutableSharedFlow<FlowPagingWorkStatus<T, L>>(Int.MAX_VALUE)
    }

    internal val sharedFlow by sharedFlowLazy

    private val parentJobMap = mutableMapOf<String, Job>()

    internal fun getParentJob(tag: String): Job {
        return parentJobMap.getOrPut(tag) {
            SupervisorJob(scope.coroutineContext.job)
        }
    }

    private val status: AtomicReference<FlowPagingWorkStatus<T, L>> by lazy {
        AtomicReference(FlowPagingWorkStatus.Initial(initData))
    }

    internal fun resetStatus(
        block: FlowPagingWorkStatus<T, L>.() -> FlowPagingWorkStatus<T, L>
    ): FlowPagingWorkStatus<T, L> {

        return status.get().block().apply(status::set)
    }

    val currentStatus: FlowPagingWorkStatus<T, L>
        get() = status.get()

    val currentData: T
        get() = currentStatus.data

    override val currentErrorMessageFlow: Flow<String>? by lazy {
        errorMessageFlow?.filterNotNull()
    }

    override val currentStatusFlow: Flow<FlowPagingWorkStatus<T, L>>? by lazy {
        statusFlow?.filterNotNull()
    }

    override val currentRefreshFlow: Flow<Boolean>? by lazy {
        statusFlow?.filterNotNull()
            ?.map { it is FlowPagingWorkStatus.Busy.Refresh }
    }

    val getter: FlowPagingWorkGetter<T, L> = this

    @ExperimentalCoroutinesApi
    fun bind() {
        if (!sharedFlowLazy.isInitialized()) {
            scope.launch(getParentJob(FlowTagUtil.getBindTag())) {

                if (errorMessageFlow != null) {
                    sharedFlow.filterIsInstance<FlowPagingWorkStatus.Error<T, L>>()
                        .map {
                            it.throwable.displayMessage
                        }.onEach {
                            errorMessageFlow.emit(it)
                        }.launchIn(this)
                }

                if (statusFlow != null) {
                    sharedFlow
                        .map {
                            statusFlow.emit(it)
                        }
                        .launchIn(this)
                }
            }
        }
    }

    suspend fun updateData(block: (T) -> T) {
        val newStatus = resetStatus {
            copy(block)
        }

        sharedFlow.emit(newStatus)
    }

    internal fun updateNextPage(pageInfo: PageInfo) {
        nextPageInfo.set(pageInfo.copy(page = pageInfo.page + 1))
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun start(isRefresh: Boolean, starter: FlowPagingStarter<T, L>.() -> Unit) {
        val pageInfo = if (isRefresh) {
            startPageInfo
        } else {
            nextPageInfo.get()
        }

        FlowPagingStarter(this).apply(starter)
            .start(isRefresh, pageInfo)
    }

    fun loadInitData(block: () -> Unit) {
        if (!sharedFlowLazy.isInitialized()) {
            block()
        }
    }
}