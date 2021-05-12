package son.ysy.lib.http.di

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import son.ysy.lib.constant.LibConstant
import son.ysy.lib.error.handler.ErrorMessageDisplayHandler
import son.ysy.lib.http.MoshiConverterFactory
import son.ysy.lib.http.error.RequestOtherErrorHandler
import son.ysy.lib.http.ext.addInterceptors
import son.ysy.lib.http.interceptor.BaseUrlInterceptor
import java.util.concurrent.TimeUnit

internal class HttpModule {
    val modules = module {
        single {
            BaseUrlInterceptor()
        } bind Interceptor::class

        single {
            RequestOtherErrorHandler()
        } bind ErrorMessageDisplayHandler::class

        single {
            val timeout = getKoin().getProperty(LibConstant.KEY_HTTP_TIMEOUT, 10L)

            OkHttpClient.Builder()
                .addInterceptors(getAll())
                .readTimeout(timeout, TimeUnit.SECONDS)
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .callTimeout(timeout, TimeUnit.SECONDS)
                .build()
        }

        single {
            MoshiConverterFactory(get())
        } bind Converter.Factory::class

        single {
            Retrofit.Builder()
                .baseUrl(getProperty(LibConstant.KEY_BASE_URL))
                .client(get())
                .addConverterFactory(get())
                .build()
        }
    }
}