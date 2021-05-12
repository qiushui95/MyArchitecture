package son.ysy.lib.http.error

import son.ysy.lib.error.MessageError

class ResponseResultError(
    val code: Int,
    override val message: String
) : MessageError(message)