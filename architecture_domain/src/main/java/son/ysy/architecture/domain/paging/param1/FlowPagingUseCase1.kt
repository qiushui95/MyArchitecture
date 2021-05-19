package son.ysy.architecture.domain.paging.param1

import kotlinx.coroutines.flow.Flow
import son.ysy.architecture.domain.paging.DomainPagingResult
import son.ysy.architecture.entity.PageInfo

interface FlowPagingUseCase1<Param, Result> {
    operator fun invoke(pageInfo: PageInfo, param: Param): Flow<DomainPagingResult<Result>>
}