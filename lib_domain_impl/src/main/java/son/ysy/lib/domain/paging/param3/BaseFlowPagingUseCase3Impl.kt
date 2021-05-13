package son.ysy.lib.domain.paging.param3

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import son.ysy.lib.domain.paging.PagingResult
import son.ysy.lib.entity.ModelResult
import son.ysy.lib.entity.PageInfo

abstract class BaseFlowPagingUseCase3Impl<Param1, Param2, Param3,
        Result> : FlowPagingUseCase3<Param1, Param2, Param3, Result> {

    override fun invoke(
        pageInfo: PageInfo,
        param1: Param1,
        param2: Param2,
        param3: Param3
    ): Flow<PagingResult<Result>> = execute(pageInfo, param1, param2, param3)
        .flowOn(Dispatchers.IO)
        .map { PagingResult(pageInfo, it) }

    protected abstract fun execute(
        pageInfo: PageInfo,
        param1: Param1,
        param2: Param2,
        param3: Param3
    ): Flow<ModelResult<Result>>
}