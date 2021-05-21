package son.ysy.architecture.http.json.annotations

import com.squareup.moshi.JsonQualifier
import son.ysy.architecture.ext.callBy
import kotlin.reflect.full.primaryConstructor

@JsonQualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class CheckResponseCode(
    val codeJsonName: String,
    val messageJsonName: String,
    vararg val codes: Int
) {
    companion object {
        fun createByReflect(
            codeJsonName: String,
            messageJsonName: String,
            vararg codes: Int
        ): CheckResponseCode {
            return CheckResponseCode::class.primaryConstructor
                ?.callBy(
                    "codeJsonName" to codeJsonName,
                    "messageJsonName" to messageJsonName,
                    "codes" to codes
                ) ?: throw NullPointerException("reflect failed!!")
        }
    }
}