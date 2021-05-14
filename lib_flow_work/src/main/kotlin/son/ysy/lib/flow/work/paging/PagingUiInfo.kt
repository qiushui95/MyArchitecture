package son.ysy.lib.flow.work.paging

interface PagingUiInfo<T : PagingUiInfo<T, L>, L> {

    fun getCurrentList(): List<L>

    fun createUiInfoFromList(list: List<L>): T
}