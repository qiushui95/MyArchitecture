package son.ysy.lib.error.di

import org.koin.dsl.bind
import org.koin.dsl.module
import son.ysy.lib.error.handler.DefaultErrorMessageDisplayHandler
import son.ysy.lib.error.handler.ErrorMessageDisplayHandler

internal class ErrorModule {

    val modules = listOf(module {
        single {
            DefaultErrorMessageDisplayHandler()
        } bind ErrorMessageDisplayHandler::class
    })
}