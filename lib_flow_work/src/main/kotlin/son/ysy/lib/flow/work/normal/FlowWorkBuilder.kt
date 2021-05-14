package son.ysy.lib.flow.work.normal

import kotlinx.coroutines.CoroutineScope
import son.ysy.lib.flow.work.FlowCreateConfig

class FlowWorkBuilder<T>(private val scope: CoroutineScope) {

    var initDataCreator: (() -> T)? = null

    var cancelBeforeIfBusy: Boolean = false

    var cancelCurrentIfBusy: Boolean = true

    var busyShareFlowConfig: FlowCreateConfig<Boolean> = FlowCreateConfig.None()

    var errorMessageFlowConfig: FlowCreateConfig<String> = FlowCreateConfig.None()

    var dataFlowConfig: FlowCreateConfig<T> = FlowCreateConfig.None()

    var statusFlowConfig: FlowCreateConfig<FlowWorkStatus<T>> = FlowCreateConfig.None()

    fun justCancelBeforeIfBusy() {
        cancelBeforeIfBusy = true
        cancelCurrentIfBusy = false
    }

    fun errorMessageFlow() {
        errorMessageFlowConfig = FlowCreateConfig.SharedFlow()
    }

    fun busyStateFlow() {
        busyShareFlowConfig = FlowCreateConfig.StateFlow()
    }

    fun busyShareFlow() {
        busyShareFlowConfig = FlowCreateConfig.SharedFlow()
    }

    fun dataStateFlow() {
        dataFlowConfig = FlowCreateConfig.StateFlow()
    }

    fun dataShareFlow() {
        dataFlowConfig = FlowCreateConfig.SharedFlow()
    }

    fun statusStateFlow() {
        statusFlowConfig = FlowCreateConfig.StateFlow()
    }

    fun statusShareFlow() {
        statusFlowConfig = FlowCreateConfig.SharedFlow()
    }

    fun build(): FlowWork<T> {
        val busyShareFlow = busyShareFlowConfig.getRealFlow()
        val errorMessageFlow = errorMessageFlowConfig.getRealFlow()
        val dataFlow = dataFlowConfig.getRealFlow()
        val statusFlow = statusFlowConfig.getRealFlow()

        return FlowWork(
            scope,
            initDataCreator,
            cancelBeforeIfBusy,
            cancelCurrentIfBusy,
            busyShareFlow,
            errorMessageFlow,
            dataFlow,
            statusFlow
        )
    }
}