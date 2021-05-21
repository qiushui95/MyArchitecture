package son.ysy.architecture.error

import org.koin.android.ext.android.getKoin
import son.ysy.architecture.constant.ArchitectureConstant
import son.ysy.architecture.error.handler.ErrorMessageDisplayHandler

val Throwable.displayMessage: String
    get() = ErrorMessageUtil.getDisplayMessage(this)

private object ErrorMessageUtil {

    private val koin by lazy {
        StarterOfArchitectureError.app.getKoin()
    }

    /**
     * 是否打印错误栈
     */
    private val printStack by lazy {
        BuildConfig.DEBUG || koin.getProperty(ArchitectureConstant.KEY_IS_DEVELOP, false)
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
            ?: StarterOfArchitectureError.app.getString(R.string.error_message_unknown)
    }
}