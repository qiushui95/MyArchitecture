package son.ysy.lib.flow.work.paging

import kotlinx.coroutines.CoroutineScope
import son.ysy.lib.entity.PageInfo
import son.ysy.lib.flow.work.FlowCreateConfig
import son.ysy.lib.flow.work.FlowWorkError

class FlowPagingWorkBuilder<T : PagingUiInfo<T, L>, L> internal constructor(
    private val scope: CoroutineScope,
    private val initData: T
) {

    var startPageInfo: PageInfo? = null


    var errorMessageFlowConfig: FlowCreateConfig<String> = FlowCreateConfig.SharedFlow()

    var statusFlowConfig: FlowCreateConfig<FlowPagingWorkStatus<T,L>> = FlowCreateConfig.SharedFlow()

    internal fun build(): FlowPagingWork<T, L> {

        val startPageInfo = startPageInfo ?: FlowPagingWork.defaultStartPageInfo
        ?: throw FlowWorkError("startPageInfo and FlowPagingWork.defaultStartPageInfo both are null,set a value to startPageInfo or set a global defaultStartPageInfo;")

        val errorMessageFlow = errorMessageFlowConfig.getRealFlow()
        val statusFlow = statusFlowConfig.getRealFlow()

        return FlowPagingWork(
            scope,
            startPageInfo,
            initData,
            errorMessageFlow,
            statusFlow
        )
    }
}