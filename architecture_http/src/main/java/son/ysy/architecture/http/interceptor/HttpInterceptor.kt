package son.ysy.architecture.http.interceptor

import okhttp3.Interceptor

/**
 * priority拦截器优先级
 * 优先级,越小越先处理
 */
abstract class HttpInterceptor(internal val priority: Int) : Interceptor