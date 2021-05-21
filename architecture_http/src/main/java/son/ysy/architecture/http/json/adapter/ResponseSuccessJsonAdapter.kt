package son.ysy.architecture.http.json.adapter

import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import son.ysy.architecture.http.entity.ResponseSuccess
import java.lang.reflect.Type

internal class ResponseSuccessJsonAdapter : HttpJsonAdapter<ResponseSuccess>() {
    override val type: Type = ResponseSuccess::class.java

    override fun fromJson(reader: JsonReader): ResponseSuccess? {
        while (reader.hasNext()) {
            reader.skipValue()
        }
        return ResponseSuccess
    }

    override fun toJson(writer: JsonWriter, value: ResponseSuccess?) {

    }
}