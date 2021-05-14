package son.ysy.lib.domain

internal fun <T> DomainResult.Companion.build(data: T?): DomainResult<T> = if (data == null) {
    DomainResult.None()
} else {
    DomainResult.Data(data)
}