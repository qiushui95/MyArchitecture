package son.ysy.architecture.domain

internal fun <T> DomainResult.Companion.build(data: T?): DomainResult<T> = if (data == null) {
    DomainResult.None()
} else {
    DomainResult.Data(data)
}