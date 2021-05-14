package son.ysy.lib.domain.normal.param3

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import son.ysy.lib.domain.DomainResult

abstract class BaseFlowUseCase3Impl<Param1, Param2, Param3,
        Result> : FlowUseCase3<Param1, Param2, Param3, Result> {

    override fun invoke(
        param1: Param1,
        param2: Param2,
        param3: Param3,
    ) = flow {
        emit(execute(param1, param2, param3))
    }.flowOn(Dispatchers.IO)
        .map {
            DomainResult.build(it)
        }

    protected abstract suspend fun execute(
        param1: Param1,
        param2: Param2,
        param3: Param3
    ): Result?
}