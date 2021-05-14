package son.ysy.lib.domain.paging.param0

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import son.ysy.lib.domain.DomainResult
import son.ysy.lib.domain.build
import son.ysy.lib.domain.paging.DomainPagingResult
import son.ysy.lib.entity.PageInfo

abstract class BaseFlowPagingUseCaseImpl<Result> : FlowPagingUseCase<Result> {

    override fun invoke(pageInfo: PageInfo): Flow<DomainPagingResult<Result>> = flow {
        emit(execute(pageInfo))
    }.map {
        DomainPagingResult(pageInfo, DomainResult.build(it))
    }

    protected abstract suspend fun execute(pageInfo: PageInfo): List<Result>?
}