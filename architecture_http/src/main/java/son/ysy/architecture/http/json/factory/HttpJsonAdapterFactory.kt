package son.ysy.architecture.http.json.factory

import com.squareup.moshi.JsonAdapter

/**
 * @param priority 优先级[0,Int.MAX_VALUE] 值越小越越先处理
 */
abstract class HttpJsonAdapterFactory(
    private val priority: Int = 100
) : JsonAdapter.Factory, Comparable<HttpJsonAdapterFactory> {

    init {
        if (priority < 0) {
            throw IllegalArgumentException("priority must in [0,Int.MAX_VALUE]")
        }
    }

    override fun compareTo(other: HttpJsonAdapterFactory): Int = priority.compareTo(other.priority)
}