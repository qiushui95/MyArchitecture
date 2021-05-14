package son.ysy.lib.domain.normal.param5

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import son.ysy.lib.domain.DomainResult
import son.ysy.lib.domain.build

abstract class BaseFlowUseCase5Impl<Param1, Param2, Param3, Param4, Param5,
        Result> : FlowUseCase5<Param1, Param2, Param3, Param4, Param5, Result> {

    override fun invoke(
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4,
        param5: Param5
    ) = executeFlow(param1, param2, param3, param4, param5)
        .map {
            DomainResult.build(it)
        }

    protected open fun executeFlow(
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4,
        param5: Param5
    ) = flow {
        emit(execute(param1, param2, param3, param4, param5))
    }

    protected abstract suspend fun execute(
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4,
        param5: Param5
    ): Result?
}