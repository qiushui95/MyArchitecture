package son.ysy.lib.error

import android.app.Application
import son.ysy.lib.starter.BaseStarter

import org.koin.android.ext.android.getKoin
import son.ysy.lib.error.di.ErrorModule

object StarterOfLibError : BaseStarter() {
    override fun execute(application: Application) {
        application.getKoin().loadModules(ErrorModule().modules)
    }
}