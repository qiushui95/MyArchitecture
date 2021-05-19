package son.ysy.architecture.ext

inline fun <reified T> List<T>.toArray(): Array<T> {
    return Array(size) { get(it) }
}