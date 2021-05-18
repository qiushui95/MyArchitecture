package son.ysy.lib.flow.work.paging

import kotlinx.coroutines.flow.Flow

interface FlowPagingWorkGetter<T : PagingUiInfo<T, L>, L> {

    val currentErrorMessageFlow: Flow<String>?

    val currentStatusFlow: Flow<FlowPagingWorkStatus<T, L>>?

    val currentRefreshFlow: Flow<Boolean>?
}