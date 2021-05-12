package son.ysy.lib.error

import org.koin.android.ext.android.getKoin
import son.ysy.lib.constant.LibConstant
import son.ysy.lib.error.handler.ErrorMessageDisplayHandler

val Throwable.displayMessage: String
    get() = ErrorMessageUtil.getDisplayMessage(this)

private object ErrorMessageUtil {

    private val koin by lazy {
        StarterOfLibError.app.getKoin()
    }

    /**
     * 是否打印错误栈
     */
    private val printStack by lazy {
        BuildConfig.DEBUG || koin.getProperty(LibConstant.KEY_IS_DEVELOP, false)
    }

    private val errorMessageHandlers by lazy {
        koin.getAll<ErrorMessageDisplayHandler>().sortedBy { it.getPriority() }
    }

    fun getDisplayMessage(error: Throwable): String {
        if (printStack) {
            error.printStackTrace()
        }

        return errorMessageHandlers.firstOrNull {
            it.handleThisError(error)
        }?.getDisplayMessage(error)
            ?: StarterOfLibError.app.getString(R.string.error_message_unknown)
    }
}