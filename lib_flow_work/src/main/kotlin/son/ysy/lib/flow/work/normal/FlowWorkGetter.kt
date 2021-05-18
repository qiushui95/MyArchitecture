package son.ysy.lib.flow.work.normal

import kotlinx.coroutines.flow.Flow

interface FlowWorkGetter<T> {

    val currentBusyFlow: Flow<Boolean>?

    val currentErrorMessageFlow: Flow<String>?

    val currentDataFlow: Flow<T?>?

    val currentStatusFlow: Flow<FlowWorkStatus<T>?>?
}