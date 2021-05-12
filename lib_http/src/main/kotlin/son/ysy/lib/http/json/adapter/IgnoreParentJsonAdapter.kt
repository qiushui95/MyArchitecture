package son.ysy.lib.http.json.adapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type

internal class IgnoreParentJsonAdapter<T>(
    moshi: Moshi,
    type: Type,
    private val jsonName: String,
    annotationSet: Set<Annotation>
) : JsonAdapter<T>() {

    private val realJsonAdapter = moshi.adapter<T>(type, annotationSet)

    override fun fromJson(reader: JsonReader): T? {

        var result: T? = null

        when (reader.peek()) {
            JsonReader.Token.BEGIN_OBJECT -> reader.beginObject()
            JsonReader.Token.BEGIN_ARRAY,
            JsonReader.Token.STRING,
            JsonReader.Token.NUMBER,
            JsonReader.Token.BOOLEAN -> {
                reader.skipValue()
                return null
            }
            JsonReader.Token.END_ARRAY -> {
                reader.endArray()
                return null
            }
            JsonReader.Token.END_OBJECT -> {
                reader.endObject()
                return null
            }
            JsonReader.Token.NAME, JsonReader.Token.NULL -> {
                reader.skipName()
                reader.skipValue()
                return null
            }
            JsonReader.Token.END_DOCUMENT -> return null
            else -> {
            }
        }
        while (reader.hasNext()) {
            when (reader.peek()) {
                JsonReader.Token.NAME -> {
                    if (reader.nextName() == jsonName) {
                        result = realJsonAdapter.fromJson(reader)
                    } else {
                        reader.skipValue()
                    }
                }

                else -> {
                    reader.skipValue()
                }
            }
        }

        if (reader.peek() == JsonReader.Token.END_OBJECT) {
            reader.endObject()
        }
        return result
    }

    override fun toJson(writer: JsonWriter, value: T?) {
        realJsonAdapter.toJson(writer, value)
    }
}