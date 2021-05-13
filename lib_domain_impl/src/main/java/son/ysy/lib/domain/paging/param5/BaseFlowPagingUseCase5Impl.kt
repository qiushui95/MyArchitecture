package son.ysy.lib.domain.paging.param5

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import son.ysy.lib.domain.paging.PagingResult
import son.ysy.lib.entity.ModelResult
import son.ysy.lib.entity.PageInfo

abstract class BaseFlowPagingUseCase5Impl<Param1, Param2, Param3, Param4, Param5,
        Result> : FlowPagingUseCase5<Param1, Param2, Param3, Param4, Param5, Result> {

    override fun invoke(
        pageInfo: PageInfo,
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4,
        param5: Param5
    ): Flow<PagingResult<Result>> = execute(pageInfo, param1, param2, param3, param4, param5)
        .flowOn(Dispatchers.IO)
        .map { PagingResult(pageInfo, it) }

    protected abstract fun execute(
        pageInfo: PageInfo,
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4,
        param5: Param5
    ): Flow<ModelResult<Result>>
}