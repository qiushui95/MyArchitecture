package son.ysy.architecture.starter

import android.app.Application
import java.util.concurrent.atomic.AtomicInteger

/**
 *
 */
abstract class BaseStarter {

    private val initTimes = AtomicInteger(0)

    lateinit var app: Application

    operator fun invoke(application: Application) {
        if (initTimes.getAndIncrement() > 0) {
            return
        }
        execute(application)

        app = application
    }

    protected abstract fun execute(application: Application)
}