package son.ysy.architecture.error.di

import org.koin.dsl.bind
import org.koin.dsl.module
import son.ysy.architecture.error.handler.DefaultErrorMessageDisplayHandler
import son.ysy.architecture.error.handler.ErrorMessageDisplayHandler

internal class ErrorModule {

    val modules = listOf(module {
        single {
            DefaultErrorMessageDisplayHandler()
        } bind ErrorMessageDisplayHandler::class
    })
}