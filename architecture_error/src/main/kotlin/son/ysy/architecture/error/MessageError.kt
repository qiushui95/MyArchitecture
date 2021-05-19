package son.ysy.architecture.error

open class MessageError(override val message: String) : RuntimeException(message)