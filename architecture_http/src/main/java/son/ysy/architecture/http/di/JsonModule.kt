package son.ysy.architecture.http.di

import com.squareup.moshi.Moshi
import org.koin.dsl.bind
import org.koin.dsl.module
import son.ysy.architecture.http.ext.addAdapters
import son.ysy.architecture.http.ext.addFactories
import son.ysy.architecture.http.json.adapter.HttpJsonAdapter
import son.ysy.architecture.http.json.adapter.ResponseSuccessJsonAdapter
import son.ysy.architecture.http.json.factory.CheckResponseCodeJsonAdapterFactory
import son.ysy.architecture.http.json.factory.HttpJsonAdapterFactory
import son.ysy.architecture.http.json.factory.IgnoreParentsJsonAdapterFactory

internal class JsonModule {

    val modules = module {

        single {
            CheckResponseCodeJsonAdapterFactory()
        } bind HttpJsonAdapterFactory::class

        single {
            IgnoreParentsJsonAdapterFactory()
        } bind HttpJsonAdapterFactory::class

        single {
            ResponseSuccessJsonAdapter()
        } bind HttpJsonAdapter::class

        factory {
            Moshi.Builder()
                .addFactories(getAll())
                .addAdapters(getAll())
                .build()
        }
    }

}