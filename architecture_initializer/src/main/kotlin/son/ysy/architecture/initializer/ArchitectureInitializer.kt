package son.ysy.architecture.initializer

import android.app.Application
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import son.ysy.architecture.constant.ArchitectureConstant
import son.ysy.architecture.entity.PageInfo

object ArchitectureInitializer {

    operator fun invoke(
        application: Application,
        isDevelop: Boolean = false,
        baseUrl: String,
        httpTimeout: Long = 10 * 1000,
        startPageInfo: PageInfo
    ) {
        startKoin {
            androidContext(application)

            if (isDevelop) {
                Level.DEBUG
            } else {
                Level.ERROR
            }.apply(this::androidLogger)
        }

        application.getKoin().apply {
            setProperty(ArchitectureConstant.KEY_IS_DEVELOP, isDevelop)
            setProperty(ArchitectureConstant.KEY_BASE_URL, baseUrl)
            setProperty(ArchitectureConstant.KEY_HTTP_TIMEOUT, httpTimeout)
            setProperty(ArchitectureConstant.KEY_DEFAULT_START_PAGE_INFO, startPageInfo)
        }
    }
}