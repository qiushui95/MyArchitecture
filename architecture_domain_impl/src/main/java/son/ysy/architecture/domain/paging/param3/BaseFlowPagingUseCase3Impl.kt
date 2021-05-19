package son.ysy.architecture.domain.paging.param3

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import son.ysy.architecture.domain.DomainResult
import son.ysy.architecture.domain.build
import son.ysy.architecture.domain.paging.DomainPagingResult
import son.ysy.architecture.entity.PageInfo

abstract class BaseFlowPagingUseCase3Impl<Param1, Param2, Param3,
        Result> : FlowPagingUseCase3<Param1, Param2, Param3, Result> {

    override fun invoke(
        pageInfo: PageInfo,
        param1: Param1,
        param2: Param2,
        param3: Param3
    ) = flow {
        emit(execute(pageInfo, param1, param2, param3))
    }.map {
        DomainPagingResult(pageInfo, DomainResult.build(it))
    }

    protected abstract suspend fun execute(
        pageInfo: PageInfo,
        param1: Param1,
        param2: Param2,
        param3: Param3
    ): List<Result>?
}