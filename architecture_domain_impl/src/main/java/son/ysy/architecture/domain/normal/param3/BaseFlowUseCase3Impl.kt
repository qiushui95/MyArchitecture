package son.ysy.architecture.domain.normal.param3

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import son.ysy.architecture.domain.DomainResult
import son.ysy.architecture.domain.build

abstract class BaseFlowUseCase3Impl<Param1, Param2, Param3,
        Result> : FlowUseCase3<Param1, Param2, Param3, Result> {

    override fun invoke(
        param1: Param1,
        param2: Param2,
        param3: Param3,
    ) = executeFlow(param1, param2, param3)
        .map {
            DomainResult.build(it)
        }

    protected open fun executeFlow(
        param1: Param1,
        param2: Param2,
        param3: Param3
    ) = flow {
        emit(execute(param1, param2, param3))
    }

    protected abstract suspend fun execute(
        param1: Param1,
        param2: Param2,
        param3: Param3
    ): Result?
}