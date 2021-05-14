package son.ysy.lib.domain.paging.param3

import kotlinx.coroutines.flow.Flow
import son.ysy.lib.domain.paging.DomainPagingResult
import son.ysy.lib.entity.PageInfo

interface FlowPagingUseCase3<Param1, Param2, Param3, Result> {
    operator fun invoke(
        pageInfo: PageInfo,
        param1: Param1,
        param2: Param2,
        param3: Param3
    ): Flow<DomainPagingResult<Result>>
}