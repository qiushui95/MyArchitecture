package son.ysy.architecture.domain.paging.param5

import kotlinx.coroutines.flow.Flow
import son.ysy.architecture.domain.paging.DomainPagingResult
import son.ysy.architecture.entity.PageInfo

interface FlowPagingUseCase5<Param1, Param2, Param3, Param4, Param5, Result> {
    operator fun invoke(
        pageInfo: PageInfo,
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4,
        param5: Param5,
    ): Flow<DomainPagingResult<Result>>
}