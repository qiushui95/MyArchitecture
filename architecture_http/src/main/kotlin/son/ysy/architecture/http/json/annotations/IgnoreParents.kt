package son.ysy.architecture.http.json.annotations

import com.squareup.moshi.JsonQualifier

@JsonQualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.FIELD)
@Repeatable
annotation class IgnoreParents(vararg val jsonNames: String, val priority: Int = Int.MAX_VALUE)
