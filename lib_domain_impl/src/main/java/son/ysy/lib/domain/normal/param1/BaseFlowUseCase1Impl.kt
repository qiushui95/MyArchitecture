package son.ysy.lib.domain.normal.param1

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import son.ysy.lib.domain.DomainResult
import son.ysy.lib.domain.build

abstract class BaseFlowUseCase1Impl<Param, Result> : FlowUseCase1<Param, Result> {

    override fun invoke(param: Param) = executeFlow(param)
        .map {
            DomainResult.build(it)
        }

    protected open fun executeFlow(param: Param) = flow {
        emit(execute(param))
    }

    protected abstract suspend fun execute(param: Param): Result?
}