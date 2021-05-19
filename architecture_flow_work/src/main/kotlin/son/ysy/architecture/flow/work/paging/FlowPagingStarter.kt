package son.ysy.architecture.flow.work.paging

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import son.ysy.architecture.domain.DomainResult
import son.ysy.architecture.entity.PageInfo
import son.ysy.architecture.flow.work.FlowTagUtil
import son.ysy.architecture.flow.work.FlowWorkError

class FlowPagingStarter<T : PagingUiInfo<T, L>, L> internal constructor(
    private val flowPagingWork: FlowPagingWork<T, L>
) {

    private val streamFlowList = mutableListOf<Flow<FlowPagingWorkStatus<T, L>>>()

    private val statusFlowList = mutableListOf<Flow<FlowPagingWorkStatus<T, L>>>()

    var pagingFlowBlock: ((PageInfo) -> Flow<T>)? = null

    private fun putIntoStreamFlow(flow: Flow<FlowPagingWorkStatus<T, L>>) {
        streamFlowList.add(flow)
    }

    private fun putIntoStatusFlow(flow: Flow<FlowPagingWorkStatus<T, L>>) {
        statusFlowList.add(flow)
    }

    private fun <T2> Flow<DomainResult<T2>>.mapFlow(mapper: (T, DomainResult<T2>) -> T): Flow<FlowPagingWorkStatus<T, L>> {
        return map { domainResult ->
            flowPagingWork.resetStatus {
                copy {
                    mapper(it, domainResult)
                }
            }
        }
    }

    fun <T2> addStreamFlow(flow: Flow<DomainResult<T2>>, mapper: (T, DomainResult<T2>) -> T) {
        putIntoStreamFlow(flow.mapFlow(mapper))
    }

    fun <T2> addStatusFlow(flow: Flow<DomainResult<T2>>, mapper: (T, DomainResult<T2>) -> T) {
        putIntoStatusFlow(flow.mapFlow(mapper))
    }

    fun <T2> addStatusIgnoreFlow(flow: Flow<DomainResult<T2>>) {
        addStatusFlow(flow) { old, _ -> old }
    }

    private fun preCheck(): Boolean {

        pagingFlowBlock ?: throw FlowWorkError("you must set a pagingFlowBlock!")

        val statusJob = flowPagingWork.getParentJob(FlowTagUtil.getStatusTag(null))

        val isBusy = statusJob.children.count() > 0

        return !isBusy
    }

    private val oldData = flowPagingWork.currentData

    @FlowPreview
    @ExperimentalCoroutinesApi
    internal fun start(
        isRefresh: Boolean,
        pageInfo: PageInfo
    ) {
        flowPagingWork.bind()

        if (!preCheck()) {
            return
        }

        flowPagingWork.scope.launch(Dispatchers.Default) {
            launch(flowPagingWork.getParentJob(FlowTagUtil.getStreamTag(null))) {
                streamFlowList.map { flow ->
                    flow.catch {
                        throw FlowWorkError("some error happen in steam flow,\n${it.message}")
                    }.flowOn(Dispatchers.IO)
                }.asFlow()
                    .flattenMerge()
                    .onEach {
                        flowPagingWork.sharedFlow.emit(it)
                    }
                    .launchIn(this)
            }

            pagingFlowBlock?.invoke(pageInfo)?.map { data ->
                flowPagingWork.resetStatus {
                    copy { data }
                }
            }?.apply(::putIntoStatusFlow)

            launch(flowPagingWork.getParentJob(FlowTagUtil.getStatusTag(null))) {
                statusFlowList.map { flow ->
                    flow.catch {
                        val data = flowPagingWork.currentData

                        emit(FlowPagingWorkStatus.Error(data, it))
                    }.flowOn(Dispatchers.IO)
                }.asFlow()
                    .flattenMerge()
                    .onStart {
                        val data = flowPagingWork.currentData

                        val busyStatus: FlowPagingWorkStatus.Busy<T, L> = if (isRefresh) {
                            FlowPagingWorkStatus.Busy.Refresh(data)
                        } else {
                            FlowPagingWorkStatus.Busy.LoadMore(data)
                        }

                        emit(busyStatus)

                    }.onCompletion {
                        when (flowPagingWork.currentStatus) {
                            is FlowPagingWorkStatus.Success, is FlowPagingWorkStatus.Error -> {
                            }
                            else -> {
                                val data = flowPagingWork.currentData

                                val newStatus = when {
                                    isRefresh && data.getCurrentList().isEmpty() -> {
                                        FlowPagingWorkStatus.Success.Empty<T, L>(data)
                                    }

                                    isRefresh -> {
                                        FlowPagingWorkStatus.Success.Empty<T, L>(data)
                                    }

                                    data.getCurrentList().size == oldData.getCurrentList().size -> {

                                        FlowPagingWorkStatus.Success.NoMoreData<T, L>(data)
                                    }

                                    else -> FlowPagingWorkStatus.Success.MoreData<T, L>(data)
                                }

                                flowPagingWork.updateNextPage(pageInfo)

                                emit(newStatus)
                            }
                        }
                    }
                    .onEach {
                        flowPagingWork.sharedFlow.emit(it)
                    }
                    .launchIn(this)
            }
        }
    }
}