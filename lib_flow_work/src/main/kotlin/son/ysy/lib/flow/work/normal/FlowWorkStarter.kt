package son.ysy.lib.flow.work.normal

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import son.ysy.lib.domain.DomainResult
import son.ysy.lib.flow.work.FlowTagUtil
import son.ysy.lib.flow.work.FlowWorkError

class FlowWorkStarter<T> internal constructor(private val flowWork: FlowWork<T>) {

    var tag: String? = null

    var cancelBeforeIfBusy: Boolean? = null

    var cancelCurrentIfBusy: Boolean? = null

    fun justCancelBeforeIfBusy() {
        cancelBeforeIfBusy = true
        cancelCurrentIfBusy = false
    }

    fun justCancelCurrentIfBusy() {
        cancelBeforeIfBusy = false
        cancelCurrentIfBusy = true
    }

    private val streamFlowList = mutableListOf<Flow<T?>>()

    private val statusFlowList = mutableListOf<Flow<T?>>()

    /**
     * 预先检查工作状态,
     * @return true:可以继续后续操作
     */
    private fun preCheck(): Boolean {
        val cancelBefore = cancelBeforeIfBusy ?: flowWork.cancelBeforeIfBusy

        val cancelCurrent = cancelCurrentIfBusy ?: flowWork.cancelCurrentIfBusy

        val statusJob = flowWork.getParentJob(FlowTagUtil.getStatusTag(tag))

        val streamJob = flowWork.getParentJob(FlowTagUtil.getStreamTag(tag))

        val isBusy = statusJob.children.count() > 0

        if (isBusy && cancelBefore) {
            statusJob.cancelChildren()

            streamJob.cancelChildren()
        }

        return !(isBusy && cancelCurrent)
    }


    private fun putIntoStreamFlow(flow: Flow<T?>) {
            streamFlowList.add(flow)
    }

    private fun putIntoStatusFlow(flow: Flow<T?>) {
            statusFlowList.add(flow)
    }

    private fun <T2> Flow<DomainResult<T2>>.mapFlow(mapper: (T?, DomainResult<T2>) -> T?) = map {
        flowWork.getNewData {
            mapper(this, it)
        }
    }

    fun addStreamFlow(flow: Flow<T?>) = putIntoStreamFlow(flow)

    fun <T2> addStreamFlow(flow: Flow<DomainResult<T2>>, mapper: (T?, DomainResult<T2>) -> T?) {
        addStreamFlow(flow.mapFlow(mapper))
    }

    fun addStatusFlow(flow: Flow<T?>) = putIntoStatusFlow(flow)

    fun <T2> addStatusFlow(flow: Flow<DomainResult<T2>>, mapper: (T?, DomainResult<T2>) -> T?) {
        addStatusFlow(flow.mapFlow(mapper))
    }

    fun <T2> addStatusIgnoreFlow(flow: Flow<DomainResult<T2>>) {
        addStatusFlow(flow) { old, _ -> old }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    internal fun start() {

        flowWork.bind()

        if (!preCheck()) {
            return
        }

        flowWork.scope.launch(Dispatchers.Default) {

            launch(flowWork.getParentJob(FlowTagUtil.getStreamTag(tag))) {
                streamFlowList.map { flow ->
                    flow.catch {
                        throw FlowWorkError("some error happen in steam flow,\n${it.message}")
                    }.flowOn(Dispatchers.IO)
                }.asFlow()
                    .flattenMerge()
                    .onEach {
                        flowWork.sharedFlow.emit(FlowEmitValue.Data(it))
                    }
                    .launchIn(this)
            }

            launch(flowWork.getParentJob(FlowTagUtil.getStatusTag(tag))) {
                statusFlowList.map { flow ->
                    flow.map<T?, FlowEmitValue<T>> {
                        FlowEmitValue.Data(it)
                    }.catch {
                        emit(FlowEmitValue.Error(it))
                    }.flowOn(Dispatchers.IO)
                }.asFlow()
                    .flattenMerge()
                    .onStart {
                        emit(FlowEmitValue.Status.Start())
                    }.onCompletion {
                        emit(FlowEmitValue.Status.Complete())
                    }
                    .onEach {
                        flowWork.sharedFlow.emit(it)
                    }
                    .launchIn(this)
            }
        }

    }
}