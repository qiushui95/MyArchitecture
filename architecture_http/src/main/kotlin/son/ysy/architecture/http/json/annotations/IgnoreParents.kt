package son.ysy.architecture.http.json.annotations

import com.squareup.moshi.JsonQualifier
import son.ysy.architecture.ext.callBy
import kotlin.reflect.full.primaryConstructor

@JsonQualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.FIELD)
@Repeatable
annotation class IgnoreParents(vararg val jsonNames: String, val priority: Int = Int.MAX_VALUE) {
    companion object {
        fun createByReflect(
            vararg jsonNames: String,
            priority: Int,
        ): IgnoreParents {
            return IgnoreParents::class.primaryConstructor
                ?.callBy(
                    "jsonNames" to jsonNames,
                    "priority" to priority
                ) ?: throw NullPointerException("reflect failed!!")
        }
    }
}
