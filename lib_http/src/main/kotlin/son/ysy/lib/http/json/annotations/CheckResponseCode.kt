package son.ysy.lib.http.json.annotations

import com.squareup.moshi.JsonQualifier

@JsonQualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class CheckResponseCode(
    val codeJsonName: String,
    val messageJsonName: String,
    vararg val codes: Int
)