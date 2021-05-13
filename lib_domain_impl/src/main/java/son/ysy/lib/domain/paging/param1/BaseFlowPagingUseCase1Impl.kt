package son.ysy.lib.domain.paging.param1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import son.ysy.lib.domain.paging.PagingResult
import son.ysy.lib.entity.ModelResult
import son.ysy.lib.entity.PageInfo

abstract class BaseFlowPagingUseCase1Impl<Param, Result> : FlowPagingUseCase1<Param, Result> {

    override fun invoke(
        pageInfo: PageInfo,
        param: Param
    ): Flow<PagingResult<Result>> = execute(pageInfo, param)
        .flowOn(Dispatchers.IO)
        .map { PagingResult(pageInfo, it) }

    protected abstract fun execute(pageInfo: PageInfo, param: Param): Flow<ModelResult<Result>>
}