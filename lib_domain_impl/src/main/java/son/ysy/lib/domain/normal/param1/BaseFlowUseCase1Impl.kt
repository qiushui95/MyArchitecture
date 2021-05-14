package son.ysy.lib.domain.normal.param1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import son.ysy.lib.domain.DomainResult

abstract class BaseFlowUseCase1Impl<Param, Result> : FlowUseCase1<Param, Result> {

    override fun invoke(param: Param) = executeFlow(param)
        .flowOn(Dispatchers.IO)
        .map {
            DomainResult.build(it)
        }

    protected open fun executeFlow(param: Param) = flow {
        emit(execute(param))
    }

    protected abstract suspend fun execute(param: Param): Result?
}