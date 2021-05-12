package son.ysy.lib.http.json.adapter

import com.squareup.moshi.JsonAdapter
import org.koin.android.ext.android.getKoin
import son.ysy.lib.constant.LibConstant
import son.ysy.lib.http.StarterOfLibHttp
import java.lang.reflect.Type

/**
 * @param priority 优先级[0,Int.MAX_VALUE] 值越小越越先处理
 */
abstract class HttpJsonAdapter<T>(
    private val priority: Int = 100
) : JsonAdapter<T>(), Comparable<HttpJsonAdapter<*>> {
    abstract val type: Type

    init {
        if (priority < 0) {
            throw IllegalArgumentException("priority must in [0,Int.MAX_VALUE]")
        }
    }

    private val koin by lazy {
        StarterOfLibHttp.app.getKoin()
    }

    protected val isDevelop: Boolean
        get() = koin.getProperty(LibConstant.KEY_IS_DEVELOP, false)

    override fun compareTo(other: HttpJsonAdapter<*>): Int = priority.compareTo(other.priority)
}