package son.ysy.lib.domain.paging.param2

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import son.ysy.lib.domain.DomainResult
import son.ysy.lib.domain.build
import son.ysy.lib.domain.paging.DomainPagingResult
import son.ysy.lib.entity.PageInfo

abstract class BaseFlowPagingUseCase2Impl<Param1, Param2, Result> :
    FlowPagingUseCase2<Param1, Param2, Result> {

    override fun invoke(
        pageInfo: PageInfo,
        param1: Param1,
        param2: Param2
    ) = flow {
        emit(execute(pageInfo, param1, param2))
    }.map {
        DomainPagingResult(pageInfo, DomainResult.build(it))
    }

    protected abstract suspend fun execute(
        pageInfo: PageInfo,
        param1: Param1,
        param2: Param2
    ): List<Result>?
}