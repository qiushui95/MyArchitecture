package son.ysy.architecture.http.error

import son.ysy.architecture.error.handler.ErrorMessageDisplayHandler
import son.ysy.architecture.http.R
import son.ysy.architecture.http.StarterOfArchitectureHttp
import java.net.SocketTimeoutException

internal class RequestTimeoutErrorHandler : ErrorMessageDisplayHandler {
    private val message by lazy {
        StarterOfArchitectureHttp.app.getString(R.string.error_message_request_timeout)
    }

    override fun getPriority(): Int = 0

    override fun handleThisError(throwable: Throwable): Boolean {
        return throwable is SocketTimeoutException
    }

    override fun getDisplayMessage(throwable: Throwable): String {
        return message
    }
}