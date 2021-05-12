package son.ysy.lib.http

import android.app.Application
import org.koin.android.ext.android.getKoin
import son.ysy.lib.error.StarterOfLibError
import son.ysy.lib.http.di.ErrorModule
import son.ysy.lib.http.di.HttpModule
import son.ysy.lib.http.di.JsonModule
import son.ysy.lib.starter.BaseStarter

object StarterOfLibHttp : BaseStarter() {

    override fun execute(application: Application) {
        StarterOfLibError(application)

        application.getKoin().loadModules(
            listOf(
                ErrorModule().modules,
                HttpModule().modules,
                JsonModule().modules,
            )
        )
    }
}