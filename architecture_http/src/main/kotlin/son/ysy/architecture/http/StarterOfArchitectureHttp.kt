package son.ysy.architecture.http

import android.app.Application
import org.koin.android.ext.android.getKoin
import son.ysy.architecture.error.StarterOfArchitectureError
import son.ysy.architecture.http.di.ErrorModule
import son.ysy.architecture.http.di.HttpModule
import son.ysy.architecture.http.di.JsonModule
import son.ysy.architecture.starter.BaseStarter

object StarterOfArchitectureHttp : BaseStarter() {

    override fun execute(application: Application) {
        StarterOfArchitectureError(application)

        application.getKoin().loadModules(
            listOf(
                ErrorModule().modules,
                HttpModule().modules,
                JsonModule().modules,
            )
        )
    }
}