package son.ysy.lib.http.json.factory

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import son.ysy.lib.http.json.adapter.CheckResponseCodeJsonAdapter
import son.ysy.lib.http.json.annotations.CheckResponseCode
import java.lang.reflect.Type

internal class CheckResponseCodeJsonAdapterFactory : HttpJsonAdapterFactory(1) {
    override fun create(
        type: Type,
        annotations: MutableSet<out Annotation>,
        moshi: Moshi
    ): JsonAdapter<*>? {

        val checkResponseCode = annotations.filterIsInstance<CheckResponseCode>()
            .firstOrNull()
            ?: return null

        val annotationSet = annotations.filterNot {
            it is CheckResponseCode
        }.toSet()

        return CheckResponseCodeJsonAdapter<Any>(type, annotationSet, moshi, checkResponseCode)
    }
}