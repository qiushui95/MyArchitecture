package son.ysy.lib.http.error

import son.ysy.lib.error.handler.ErrorMessageDisplayHandler
import son.ysy.lib.http.R
import son.ysy.lib.http.StarterOfLibHttp
import java.net.SocketTimeoutException

internal class RequestTimeoutErrorHandler : ErrorMessageDisplayHandler {
    private val message by lazy {
        StarterOfLibHttp.app.getString(R.string.error_message_request_timeout)
    }

    override fun getPriority(): Int = 0

    override fun handleThisError(throwable: Throwable): Boolean {
        return throwable is SocketTimeoutException
    }

    override fun getDisplayMessage(throwable: Throwable): String {
        return message
    }
}