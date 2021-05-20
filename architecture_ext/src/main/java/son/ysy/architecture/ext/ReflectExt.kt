package son.ysy.architecture.ext

import kotlin.reflect.KFunction
import kotlin.reflect.KParameter

fun <T> KFunction<T>.callBy(vararg paramPair: Pair<String, Any?>): T {
    val map = mutableMapOf<KParameter, Any?>()

    parameters.forEach { kParameter ->
        map[kParameter] = paramPair.firstOrNull { it.first == kParameter.name }?.second
    }

    return callBy(map)
}