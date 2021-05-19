package son.ysy.architecture.http.error

import son.ysy.architecture.error.MessageError

class ResponseResultError(
    val code: Int,
    override val message: String
) : MessageError(message)