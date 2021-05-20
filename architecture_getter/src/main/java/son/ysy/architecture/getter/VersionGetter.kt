package son.ysy.architecture.getter

interface VersionGetter {

    fun getVersionName(): String

    fun getVersionCode(): Int

    fun getHotFixCode(): Int
}