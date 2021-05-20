package son.ysy.architecture.getter

interface TokenGetter {

    suspend fun getUserToken(): String?
}