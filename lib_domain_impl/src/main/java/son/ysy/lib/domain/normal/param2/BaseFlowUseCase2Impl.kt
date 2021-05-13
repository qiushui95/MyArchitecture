package son.ysy.lib.domain.normal.param2

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import son.ysy.lib.domain.DomainResult

abstract class BaseFlowUseCase2Impl<Param1, Param2, Result> : FlowUseCase2<Param1, Param2, Result> {

    override fun invoke(param1: Param1, param2: Param2) = flow {
        emit(execute(param1, param2))
    }.flowOn(Dispatchers.IO)
        .map {
            DomainResult.build(it)
        }

    protected abstract fun execute(param1: Param1, param2: Param2): Result?
}