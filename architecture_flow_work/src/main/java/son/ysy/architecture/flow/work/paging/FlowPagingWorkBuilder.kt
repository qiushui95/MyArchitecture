package son.ysy.architecture.flow.work.paging

import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.android.getKoin
import son.ysy.architecture.constant.ArchitectureConstant
import son.ysy.architecture.entity.PageInfo
import son.ysy.architecture.flow.work.FlowCreateConfig
import son.ysy.architecture.flow.work.FlowWorkError
import son.ysy.architecture.flow.work.StarterOfArchitectureFlowWork

class FlowPagingWorkBuilder<T : PagingUiInfo<T, L>, L> internal constructor(
    private val scope: CoroutineScope,
    private val initData: T
) {

    var startPageInfo: PageInfo? = null


    var errorMessageFlowConfig: FlowCreateConfig<String> = FlowCreateConfig.SharedFlow()

    var statusFlowConfig: FlowCreateConfig<FlowPagingWorkStatus<T, L>> =
        FlowCreateConfig.SharedFlow()

    internal fun build(): FlowPagingWork<T, L> {

        val startPageInfo = startPageInfo
            ?: StarterOfArchitectureFlowWork
                .app
                .getKoin()
                .getProperty(ArchitectureConstant.KEY_DEFAULT_START_PAGE_INFO)
            ?: throw FlowWorkError("startPageInfo and defaultStartPageInfo both are null,set a value to startPageInfo or set a global defaultStartPageInfo;")

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