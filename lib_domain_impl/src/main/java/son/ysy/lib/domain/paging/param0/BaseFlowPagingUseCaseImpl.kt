package son.ysy.lib.domain.paging.param0

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import son.ysy.lib.domain.DomainResult
import son.ysy.lib.domain.paging.PagingResult
import son.ysy.lib.entity.PageInfo

abstract class BaseFlowPagingUseCaseImpl<Result> : FlowPagingUseCase<Result> {

    override fun invoke(pageInfo: PageInfo): Flow<PagingResult<Result>> = flow {
        emit(execute(pageInfo))
    }.flowOn(Dispatchers.IO)
        .map {
            PagingResult(pageInfo, DomainResult.build(it))
        }

    protected abstract suspend fun execute(pageInfo: PageInfo): List<Result>?
}