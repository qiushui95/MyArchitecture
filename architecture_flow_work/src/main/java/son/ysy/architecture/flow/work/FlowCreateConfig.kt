package son.ysy.architecture.flow.work

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

sealed class FlowCreateConfig<T> {
    class None<T> : FlowCreateConfig<T>()

    class SharedFlow<T> : FlowCreateConfig<T>()

    class StateFlow<T> : FlowCreateConfig<T>()

    data class DefineFlow<T>(val flow: MutableSharedFlow<T?>) :
        FlowCreateConfig<T>()

    internal fun getRealFlow(): MutableSharedFlow<T?>? {
        return when (this) {
            is None -> null
            is SharedFlow -> MutableSharedFlow()
            is StateFlow -> MutableStateFlow(null)
            is DefineFlow -> flow
        }
    }
}