package son.ysy.lib.domain.paging.param1

import kotlinx.coroutines.flow.Flow
import son.ysy.lib.domain.paging.PagingResult
import son.ysy.lib.entity.PageInfo

interface FlowPagingUseCase1<Param, Result> {
    operator fun invoke(pageInfo: PageInfo, param: Param): Flow<PagingResult<Result>>
}