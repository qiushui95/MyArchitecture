package son.ysy.architecture.error

fun interface ErrorReporter {

    /**
     * 上报错误
     */
    fun report(ex: Throwable)
}