package son.ysy.architecture.flow.work.paging

sealed class FlowPagingWorkStatus<T, L>(val data: T) {

    class Initial<T, L>(data: T) : FlowPagingWorkStatus<T, L>(data)

    sealed class Busy<T, L>(data: T) : FlowPagingWorkStatus<T, L>(data) {

        class Refresh<T, L>(data: T) : Busy<T, L>(data)

        class LoadMore<T, L>(data: T) : Busy<T, L>(data)
    }

    class Error<T, L>(data: T, val throwable: Throwable) : FlowPagingWorkStatus<T, L>(data)

    sealed class Success<T, L>(data: T) : FlowPagingWorkStatus<T, L>(data) {
        class Empty<T, L>(data: T) : Success<T, L>(data)

        class NoMoreData<T, L>(data: T) : Success<T, L>(data)

        class MoreData<T, L>(data: T) : Success<T, L>(data)
    }

    fun <T1, L1> copy(copyDataBlock: (T) -> T1): FlowPagingWorkStatus<T1, L1> {
        val data = copyDataBlock(data)
        return when (this) {
            is Busy.LoadMore -> Busy.LoadMore(data)
            is Busy.Refresh -> Busy.Refresh(data)
            is Error -> Error(data, throwable)
            is Initial -> Initial(data)
            is Success.Empty -> Success.Empty(data)
            is Success.MoreData -> Success.MoreData(data)
            is Success.NoMoreData -> Success.NoMoreData(data)
        }
    }
}