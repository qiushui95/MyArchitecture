package son.ysy.lib.http.json.adapter

import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.internal.Util
import son.ysy.lib.http.error.ResponseResultError
import son.ysy.lib.http.json.annotations.CheckResponseCode
import java.lang.reflect.Type

internal class CheckResponseCodeJsonAdapter<T>(
    override val type: Type,
    annotations: Set<Annotation>,
    moshi: Moshi,
    private val checkResponseCode: CheckResponseCode
) : HttpJsonAdapter<T>() {

    private val realAdapter = moshi.adapter<T>(type, annotations)

    private val options = JsonReader.Options.of(
        checkResponseCode.codeJsonName,
        checkResponseCode.messageJsonName
    )

    override fun fromJson(reader: JsonReader): T? {

        val peekReader = reader.peekJson()
        peekReader.beginObject()

        var code: Int? = null
        var msg: String? = null

        while (peekReader.hasNext()) {
            when (peekReader.selectName(options)) {
                0 -> code = peekReader.nextInt()
                1 -> msg = peekReader.nextString()
                else -> peekReader.skipValue()
            }
        }
        peekReader.endObject()

        when {
            code == null -> throw Util.unexpectedNull("", checkResponseCode.codeJsonName, reader)

            code !in checkResponseCode.codes && msg != null -> throw ResponseResultError(
                code,
                msg.takeUnless { isDevelop } ?: "$code:${msg}"
            )

            code !in checkResponseCode.codes -> throw ResponseResultError(code, "未知服务器异常,状态码:$code")
        }

        return realAdapter.fromJson(reader)
    }

    override fun toJson(writer: JsonWriter, value: T?) {
        realAdapter.toJson(writer, value)
    }
}