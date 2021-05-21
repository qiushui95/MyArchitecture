package son.ysy.architecture.initializer

import android.app.Application
import android.content.Context
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.bind
import org.koin.dsl.module
import son.ysy.architecture.constant.ArchitectureConstant
import son.ysy.architecture.entity.PageInfo
import son.ysy.architecture.getter.TokenGetter
import son.ysy.architecture.getter.VersionGetter
import son.ysy.architecture.http.json.annotations.CheckResponseCode
import son.ysy.architecture.http.json.annotations.IgnoreParents

object ArchitectureInitializer {

    operator fun invoke(
        application: Application,
        isDevelop: Boolean = false,
        baseUrl: String,
        httpTimeout: Long = 10 * 1000,
        startPageInfo: PageInfo,
        tokenGetter: () -> TokenGetter,
        versionGetter: () -> VersionGetter,
        defaultCheckResponseCode: () -> CheckResponseCode,
        defaultIgnoreParents: () -> IgnoreParents,
    ) {
        startKoin {
            androidContext(application)

            if (isDevelop) {
                Level.DEBUG
            } else {
                Level.ERROR
            }.apply(this::androidLogger)

            modules(module {
                single {
                    koin
                }

                single {
                    tokenGetter()
                } bind TokenGetter::class

                single {
                    versionGetter()
                } bind VersionGetter::class

                single {
                    defaultCheckResponseCode()
                }

                single {
                    defaultIgnoreParents()
                }
            })
        }

        application.getKoin().apply {
            setProperty(ArchitectureConstant.KEY_IS_DEVELOP, isDevelop)
            setProperty(ArchitectureConstant.KEY_BASE_URL, baseUrl)
            setProperty(ArchitectureConstant.KEY_HTTP_TIMEOUT, httpTimeout)
            setProperty(ArchitectureConstant.KEY_DEFAULT_START_PAGE_INFO, startPageInfo)
        }
    }
}