package son.ysy.architecture.flow.work.normal

internal sealed class FlowEmitValue<T> {
    sealed class Status<T>(val isBusy: Boolean) : FlowEmitValue<T>() {
        class Start<T> : Status<T>(true)
        class Complete<T> : Status<T>(false)
    }

    data class Error<T>(val throwable: Throwable) : FlowEmitValue<T>()

    data class Data<T>(val data: T?) : FlowEmitValue<T>()
}