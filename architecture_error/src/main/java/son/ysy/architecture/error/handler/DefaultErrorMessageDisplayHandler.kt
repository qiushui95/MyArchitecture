package son.ysy.architecture.error.handler

import son.ysy.architecture.error.MessageError

internal class DefaultErrorMessageDisplayHandler : ErrorMessageDisplayHandler {
    override fun getPriority(): Int = Int.MAX_VALUE

    override fun handleThisError(throwable: Throwable): Boolean = throwable is MessageError

    override fun getDisplayMessage(
        throwable: Throwable
    ): String = (throwable as MessageError).message
}