package son.ysy.architecture.http.di

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import son.ysy.architecture.constant.ArchitectureConstant
import son.ysy.architecture.error.handler.ErrorMessageDisplayHandler
import son.ysy.architecture.http.MoshiConverterFactory
import son.ysy.architecture.http.error.RequestOtherErrorHandler
import son.ysy.architecture.http.ext.addInterceptors
import son.ysy.architecture.http.interceptor.BaseUrlInterceptor
import java.util.concurrent.TimeUnit

internal class HttpModule {
    val modules = module {
        single {
            BaseUrlInterceptor()
        } bind Interceptor::class

        single {
            val timeout = getKoin().getProperty(ArchitectureConstant.KEY_HTTP_TIMEOUT, 10L)

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
                .baseUrl(getProperty(ArchitectureConstant.KEY_BASE_URL))
                .client(get())
                .addConverterFactory(get())
                .build()
        }
    }
}