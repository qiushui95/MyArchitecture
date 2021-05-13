package son.ysy.lib.domain.paging.param4

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import son.ysy.lib.domain.DomainResult
import son.ysy.lib.domain.paging.PagingResult
import son.ysy.lib.entity.PageInfo

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
    }.flowOn(Dispatchers.IO)
        .map {
            PagingResult(pageInfo, DomainResult.build(it))
        }

    protected abstract fun execute(
        pageInfo: PageInfo,
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4
    ): List<Result>?
}