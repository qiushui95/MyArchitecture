package son.ysy.architecture.error.handler

interface ErrorMessageDisplayHandler {

    /**
     * 获取处理器优先级
     * @return 优先级,越小越先处理
     */
    fun getPriority(): Int

    /**
     * 是否处理当前错误
     * @param throwable 错误
     * @return true 能处理当前错误
     */
    fun handleThisError(throwable: Throwable): Boolean

    /**
     * 可获取展示错误信息
     * @param throwable 错误
     * @return 课展示错误信息
     */
    fun getDisplayMessage(throwable: Throwable): String
}