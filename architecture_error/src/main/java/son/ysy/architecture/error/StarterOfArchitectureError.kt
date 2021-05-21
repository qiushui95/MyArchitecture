package son.ysy.architecture.error

import android.app.Application
import org.koin.android.ext.android.getKoin
import son.ysy.architecture.error.di.ErrorModule
import son.ysy.architecture.starter.BaseStarter

object StarterOfArchitectureError : BaseStarter() {
    override fun execute(application: Application) {
        application.getKoin().loadModules(ErrorModule().modules)
    }
}