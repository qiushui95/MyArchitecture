package son.ysy.lib.domain.paging.param2

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import son.ysy.lib.domain.paging.PagingResult
import son.ysy.lib.entity.ModelResult
import son.ysy.lib.entity.PageInfo

abstract class BaseFlowPagingUseCase2Impl<Param1, Param2, Result> :
    FlowPagingUseCase2<Param1, Param2, Result> {

    override fun invoke(
        pageInfo: PageInfo,
        param1: Param1,
        param2: Param2
    ): Flow<PagingResult<Result>> = execute(pageInfo, param1, param2)
        .flowOn(Dispatchers.IO)
        .map { PagingResult(pageInfo, it) }

    protected abstract fun execute(
        pageInfo: PageInfo,
        param1: Param1,
        param2: Param2
    ): Flow<ModelResult<Result>>
}