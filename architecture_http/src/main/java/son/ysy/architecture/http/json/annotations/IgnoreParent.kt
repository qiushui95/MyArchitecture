package son.ysy.architecture.http.json.annotations

import com.squareup.moshi.JsonQualifier

@JsonQualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
internal annotation class IgnoreParent(val jsonName: String)
