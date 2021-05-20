package son.ysy.architecture.starter

import android.app.Application
import java.util.concurrent.atomic.AtomicBoolean

/**
 *
 */
abstract class BaseStarter {

    private val hasStart = AtomicBoolean(false)

    lateinit var app: Application

    operator fun invoke(application: Application) {
        if (hasStart.get()) {
            return
        }
        app = application

        hasStart.set(true)

        execute(application)
    }

    protected abstract fun execute(application: Application)
}