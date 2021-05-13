package son.ysy.lib.domain

sealed class DomainResult<T> {
    companion object {
        fun <T> build(data: T?): DomainResult<T> = if (data == null) {
            None()
        } else {
            Data(data)
        }
    }

    data class Data<T>(val data: T) : DomainResult<T>()

    class None<T> : DomainResult<T>()
}
