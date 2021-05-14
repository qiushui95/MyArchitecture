package son.ysy.lib.domain.normal.param4

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import son.ysy.lib.domain.DomainResult

abstract class BaseFlowUseCase4Impl<Param1, Param2, Param3, Param4, Result> :
    FlowUseCase4<Param1, Param2, Param3, Param4, Result> {

    override fun invoke(
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4
    ) = executeFlow(param1, param2, param3, param4)
        .flowOn(Dispatchers.IO)
        .map {
            DomainResult.build(it)
        }

    protected open fun executeFlow(
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4
    ) = flow {
        emit(execute(param1, param2, param3, param4))
    }

    protected abstract suspend fun execute(
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4
    ): Result?
}