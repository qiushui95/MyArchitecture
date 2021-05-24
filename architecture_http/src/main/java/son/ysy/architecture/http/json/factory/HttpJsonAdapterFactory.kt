package son.ysy.architecture.http.json.factory

import com.squareup.moshi.JsonAdapter

/**
 * @param priority 优先级,值越小越越先处理
 */
abstract class HttpJsonAdapterFactory(
    private val priority: Int = 100
) : JsonAdapter.Factory, Comparable<HttpJsonAdapterFactory> {

    override fun compareTo(other: HttpJsonAdapterFactory): Int = priority.compareTo(other.priority)
}