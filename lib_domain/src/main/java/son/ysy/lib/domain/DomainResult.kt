package son.ysy.lib.domain

sealed class DomainResult<T> {
    companion object {}

    data class Data<T>(val data: T) : DomainResult<T>()

    class None<T> : DomainResult<T>()
}
