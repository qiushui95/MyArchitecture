package son.ysy.architecture.domain.paging.param0

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import son.ysy.architecture.domain.DomainResult
import son.ysy.architecture.domain.build
import son.ysy.architecture.domain.paging.DomainPagingResult
import son.ysy.architecture.entity.PageInfo

abstract class BaseFlowPagingUseCaseImpl<Result> : FlowPagingUseCase<Result> {

    override fun invoke(pageInfo: PageInfo): Flow<DomainPagingResult<Result>> = flow {
        emit(execute(pageInfo))
    }.map {
        DomainPagingResult(pageInfo, DomainResult.build(it))
    }

    protected abstract suspend fun execute(pageInfo: PageInfo): List<Result>?
}