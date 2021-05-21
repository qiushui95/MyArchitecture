package son.ysy.architecture.http.ext

import com.squareup.moshi.Moshi
import son.ysy.architecture.http.json.adapter.HttpJsonAdapter
import son.ysy.architecture.http.json.factory.HttpJsonAdapterFactory

internal fun Moshi.Builder.addFactories(list: List<HttpJsonAdapterFactory>): Moshi.Builder {
    list.sorted().forEach(::add)
    return this
}

internal fun Moshi.Builder.addAdapters(list: List<HttpJsonAdapter<*>>): Moshi.Builder {
    list.sorted().forEach {
        add(it.type, it)
    }
    return this
}