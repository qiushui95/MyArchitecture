package son.ysy.lib.model.repository

import son.ysy.lib.model.ModelResult

abstract class BaseRepositoryImpl {
    protected inline fun <reified T> T.toResultData(): ModelResult.Data<T> {
        return ModelResult.Data(this)
    }

    protected inline fun <reified T, reified R> T.toResultData(converter: T.() -> R): ModelResult.Data<R> {
        return ModelResult.Data(converter())
    }

    protected inline fun <reified T, reified R> List<T>.toResultData(converter: T.() -> R): ModelResult.Data<List<R>> {
        return ModelResult.Data(map { it.converter() })
    }

    protected inline fun <reified T> resultNone() = ModelResult.None<T>()
}