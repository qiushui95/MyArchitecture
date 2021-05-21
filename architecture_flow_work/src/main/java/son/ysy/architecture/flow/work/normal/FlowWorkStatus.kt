package son.ysy.architecture.flow.work.normal

import son.ysy.architecture.error.displayMessage


sealed class FlowWorkStatus<T>(
    val data: T?,
    val isBusy: Boolean,
    val isSuccess: Boolean,
) {

    val isComplete: Boolean = !isBusy

    val isCompleteSuccess: Boolean = isComplete && isSuccess

    class Success<T>(
        isBusy: Boolean,
        data: T?
    ) : FlowWorkStatus<T>(data, isBusy, true)

    class Error<T>(
        isBusy: Boolean,
        data: T?,
        val error: Throwable,
        val message: String
    ) : FlowWorkStatus<T>(data, isBusy, false) {
        constructor(
            isBusy: Boolean,
            data: T?,
            error: Throwable,
        ) : this(isBusy, data, error, error.displayMessage)
    }

    class Busy<T>(
        data: T?,
    ) : FlowWorkStatus<T>(data, true, false)

    fun <R> copyNewData(block: (T?) -> R?): FlowWorkStatus<R> {
        val newData = block(data)
        return when (this) {
            is Success<T> -> Success(isBusy, newData)
            is Error<T> -> Error(isBusy, newData, error, message)
            is Busy<T> -> Busy(newData)
        }
    }

    fun copyNewBusy(isBusy: Boolean): FlowWorkStatus<T> {
        return when (this) {
            is Success<T> -> Success(isBusy, data)
            is Error<T> -> Error(isBusy, data, error, message)
            is Busy<T> -> Busy(data)
        }
    }

    override fun toString(): String {
        val sb = StringBuilder("DoWorkStatus.")
        when (this) {
            is Busy -> sb.append("Busy")
            is Error -> sb.append("Error")
            is Success -> sb.append("Success")
        }
        sb.append("(data=$data, isBusy=$isBusy, isSuccess=$isSuccess)\"")
        return sb.toString()
    }


}