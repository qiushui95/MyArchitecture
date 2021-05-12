package son.ysy.lib.ext

inline fun <reified T> List<T>.toArray(): Array<T> {
    return Array(size) { get(it) }
}