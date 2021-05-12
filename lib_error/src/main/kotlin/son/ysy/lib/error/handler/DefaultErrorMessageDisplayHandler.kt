package son.ysy.lib.error.handler

import son.ysy.lib.error.MessageError

internal class DefaultErrorMessageDisplayHandler : ErrorMessageDisplayHandler {
    override fun getPriority(): Int = Int.MAX_VALUE

    override fun handleThisError(throwable: Throwable): Boolean = throwable is MessageError

    override fun getDisplayMessage(
        throwable: Throwable
    ): String = (throwable as MessageError).message
}