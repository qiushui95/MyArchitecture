package son.ysy.architecture.ext

import java.io.File

fun File.mkdirIfNotExists(): Boolean {
    if (exists()) {
        return true
    }

    return mkdir()
}

fun File.mkdirsIfNotExists(): Boolean {
    if (exists()) {
        return true
    }

    return mkdirs()
}