package son.ysy.lib.domain.paging.param0

import kotlinx.coroutines.flow.Flow
import son.ysy.lib.domain.paging.PagingResult
import son.ysy.lib.entity.PageInfo

interface FlowPagingUseCase<Result> {
    operator fun invoke(pageInfo: PageInfo): Flow<PagingResult<Result>>
}