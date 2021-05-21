package son.ysy.architecture.http.di

import org.koin.dsl.bind
import org.koin.dsl.module
import son.ysy.architecture.error.handler.ErrorMessageDisplayHandler
import son.ysy.architecture.http.error.RequestOtherErrorHandler
import son.ysy.architecture.http.error.RequestTimeoutErrorHandler

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