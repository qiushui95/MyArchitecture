package son.ysy.architecture.domain.paging.param4

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import son.ysy.architecture.domain.DomainResult
import son.ysy.architecture.domain.build
import son.ysy.architecture.domain.paging.DomainPagingResult
import son.ysy.architecture.entity.PageInfo

abstract class BaseFlowPagingUseCase4Impl<Param1, Param2, Param3, Param4,
        Result> : FlowPagingUseCase4<Param1, Param2, Param3, Param4, Result> {

    override fun invoke(
        pageInfo: PageInfo,
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4
    ) = flow {
        emit(execute(pageInfo, param1, param2, param3, param4))
    }.map {
        DomainPagingResult(pageInfo, DomainResult.build(it))
    }

    protected abstract suspend fun execute(
        pageInfo: PageInfo,
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4
    ): List<Result>?
}