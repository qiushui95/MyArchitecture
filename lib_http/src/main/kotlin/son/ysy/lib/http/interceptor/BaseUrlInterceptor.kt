package son.ysy.lib.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class BaseUrlInterceptor : Interceptor {

    private var baseUrl: String? = null

    fun changeBaseUrl(baseUrl: String): Unit {
        this.baseUrl = baseUrl
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val baseUrl = baseUrl

        val request = chain.request()

        return if (baseUrl != null) {

            val url = request.url
                .newBuilder()
                .host(baseUrl)
                .build()

            request.newBuilder()
                .url(url)
                .build()
        } else {
            request
        }.run(chain::proceed)
    }
}