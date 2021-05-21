package son.ysy.architecture.flow.work.paging

@JvmInline
value class PagingList<T> private constructor(
    private val value: List<T>
) : PagingUiInfo<PagingList<T>, T> {
    companion object {
        fun <T> empty() = PagingList<T>(emptyList())

        fun <T> list(list: List<T>) = PagingList(list)
    }

    override fun getCurrentList(): List<T> = value

    override fun createUiInfoFromList(list: List<T>): PagingList<T> = PagingList(value = list)
}
