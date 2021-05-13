package son.ysy.lib.domain.paging.param0

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import son.ysy.lib.domain.paging.PagingResult
import son.ysy.lib.entity.ModelResult
import son.ysy.lib.entity.PageInfo

abstract class BaseFlowPagingUseCaseImpl<Result> : FlowPagingUseCase<Result> {

    override fun invoke(pageInfo: PageInfo): Flow<PagingResult<Result>> = execute(pageInfo)
        .flowOn(Dispatchers.IO)
        .map { PagingResult(pageInfo, it) }

    protected abstract fun execute(pageInfo: PageInfo): Flow<ModelResult<Result>>
}