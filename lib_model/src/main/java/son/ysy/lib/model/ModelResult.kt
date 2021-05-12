package son.ysy.lib.model

sealed class ModelResult<T> {
    data class Data<T>(val data: T) : ModelResult<T>()

    class None<T> : ModelResult<T>()
}
