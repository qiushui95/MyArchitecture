package son.ysy.lib.http.ext

import okhttp3.Interceptor
import okhttp3.OkHttpClient


internal fun OkHttpClient.Builder.addInterceptors(list: List<Interceptor>): OkHttpClient.Builder {
    list.forEach(::addInterceptor)
    return this
}