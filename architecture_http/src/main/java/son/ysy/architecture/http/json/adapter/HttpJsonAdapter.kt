package son.ysy.architecture.http.json.adapter

import com.squareup.moshi.JsonAdapter
import org.koin.android.ext.android.getKoin
import son.ysy.architecture.constant.ArchitectureConstant
import son.ysy.architecture.http.StarterOfArchitectureHttp
import java.lang.reflect.Type

/**
 * @param priority 优先级,值越小越越先处理
 */
abstract class HttpJsonAdapter<T>(
    private val priority: Int = 100
) : JsonAdapter<T>(), Comparable<HttpJsonAdapter<*>> {
    abstract val type: Type

    private val koin by lazy {
        StarterOfArchitectureHttp.app.getKoin()
    }

    protected val isDevelop: Boolean
        get() = koin.getProperty(ArchitectureConstant.KEY_IS_DEVELOP, false)

    override fun compareTo(other: HttpJsonAdapter<*>): Int = priority.compareTo(other.priority)
}