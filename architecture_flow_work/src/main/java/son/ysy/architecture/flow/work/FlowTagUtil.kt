package son.ysy.architecture.flow.work

internal object FlowTagUtil {

    private const val TAG_BIND = "bind"

    private const val TAG_PREFIX_STATUS = "status"

    private const val TAG_PREFIX_STREAM = "stream"

    fun getBindTag() = TAG_BIND

    fun getStatusTag(tag: String?) = "$TAG_PREFIX_STATUS$tag"

    fun getStreamTag(tag: String?) = "$TAG_PREFIX_STREAM$tag"
}