package son.ysy.architecture.http.error

import son.ysy.architecture.error.handler.ErrorMessageDisplayHandler
import son.ysy.architecture.http.R
import son.ysy.architecture.http.StarterOfArchitectureHttp
import java.io.InterruptedIOException
import java.net.SocketException
import java.net.UnknownHostException

internal class RequestOtherErrorHandler : ErrorMessageDisplayHandler {

    private val message by lazy {
        StarterOfArchitectureHttp.app.getString(R.string.error_message_request_error)
    }

    override fun getPriority(): Int = 0

    override fun handleThisError(throwable: Throwable): Boolean {
        return throwable is SocketException || throwable is UnknownHostException || throwable is InterruptedIOException
    }

    override fun getDisplayMessage(throwable: Throwable): String {
        return message
    }
}