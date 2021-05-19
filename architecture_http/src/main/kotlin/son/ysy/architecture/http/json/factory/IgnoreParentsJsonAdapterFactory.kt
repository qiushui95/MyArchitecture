package son.ysy.architecture.http.json.factory

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import son.ysy.architecture.http.json.adapter.IgnoreParentJsonAdapter
import son.ysy.architecture.http.json.annotations.IgnoreParent
import son.ysy.architecture.http.json.annotations.IgnoreParents
import java.lang.reflect.Type
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor

internal class IgnoreParentsJsonAdapterFactory : HttpJsonAdapterFactory(2) {

    private val ignoreParentPrimaryConstructor by lazy {
        IgnoreParent::class.primaryConstructor
    }

    override fun create(
        type: Type,
        annotations: MutableSet<out Annotation>,
        moshi: Moshi
    ): JsonAdapter<*>? {

        val newAnnotations: MutableList<Annotation> = annotations.filterIsInstance<IgnoreParents>()
            .sortedBy { it.priority }
            .flatMap { parents ->

                parents.jsonNames.mapNotNull { jsonName ->
                    val paramMap = mutableMapOf<KParameter, Any?>()
                    ignoreParentPrimaryConstructor?.parameters?.forEach {
                        paramMap[it] = jsonName
                    }
                    ignoreParentPrimaryConstructor?.callBy(paramMap)
                }
            }.toMutableList()

        annotations.filterNot { it is IgnoreParents }.apply(newAnnotations::addAll)

        val ignoreParent = newAnnotations.filterIsInstance<IgnoreParent>()
            .firstOrNull()
            ?: return null

        val nextAnnotations = newAnnotations.filterNot { it == ignoreParent }.toSet()

        return IgnoreParentJsonAdapter<Any>(moshi, type, ignoreParent.jsonName, nextAnnotations)
    }
}