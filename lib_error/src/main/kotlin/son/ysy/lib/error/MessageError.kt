package son.ysy.lib.error

open class MessageError(override val message: String) : RuntimeException(message)