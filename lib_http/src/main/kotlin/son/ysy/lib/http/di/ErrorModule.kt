package son.ysy.lib.http.di

import org.koin.dsl.bind
import org.koin.dsl.module
import son.ysy.lib.error.handler.ErrorMessageDisplayHandler
import son.ysy.lib.http.error.RequestOtherErrorHandler
import son.ysy.lib.http.error.RequestTimeoutErrorHandler

internal class ErrorModule {

    val modules = module {
        single {
            RequestTimeoutErrorHandler()
        } bind ErrorMessageDisplayHandler::class

        single {
            RequestOtherErrorHandler()
        } bind ErrorMessageDisplayHandler::class
    }
}