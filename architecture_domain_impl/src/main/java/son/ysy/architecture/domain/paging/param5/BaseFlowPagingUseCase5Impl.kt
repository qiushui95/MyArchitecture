package son.ysy.architecture.domain.paging.param5

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import son.ysy.architecture.domain.DomainResult
import son.ysy.architecture.domain.build
import son.ysy.architecture.domain.paging.DomainPagingResult
import son.ysy.architecture.entity.PageInfo

abstract class BaseFlowPagingUseCase5Impl<Param1, Param2, Param3, Param4, Param5,
        Result> : FlowPagingUseCase5<Param1, Param2, Param3, Param4, Param5, Result> {

    override fun invoke(
        pageInfo: PageInfo,
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4,
        param5: Param5
    ) = flow {
        emit(execute(pageInfo, param1, param2, param3, param4, param5))
    }.map {
        DomainPagingResult(pageInfo, DomainResult.build(it))
    }

    protected abstract suspend fun execute(
        pageInfo: PageInfo,
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4,
        param5: Param5
    ): List<Result>?
}