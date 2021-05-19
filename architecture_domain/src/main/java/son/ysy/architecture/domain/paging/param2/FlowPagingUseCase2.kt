package son.ysy.architecture.domain.paging.param2

import kotlinx.coroutines.flow.Flow
import son.ysy.architecture.domain.paging.DomainPagingResult
import son.ysy.architecture.entity.PageInfo

interface FlowPagingUseCase2<Param1, Param2, Result> {
    operator fun invoke(
        pageInfo: PageInfo,
        param1: Param1,
        param2: Param2
    ): Flow<DomainPagingResult<Result>>
}