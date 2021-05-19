package son.ysy.architecture.domain.paging.param0

import kotlinx.coroutines.flow.Flow
import son.ysy.architecture.domain.paging.DomainPagingResult
import son.ysy.architecture.entity.PageInfo

interface FlowPagingUseCase<Result> {
    operator fun invoke(pageInfo: PageInfo): Flow<DomainPagingResult<Result>>
}