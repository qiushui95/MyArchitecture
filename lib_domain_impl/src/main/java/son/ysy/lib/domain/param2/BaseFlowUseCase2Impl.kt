package son.ysy.lib.domain.param2

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseFlowUseCase2Impl<Param1, Param2, Result> : FlowUseCase2<Param1, Param2, Result> {

    override fun invoke(param1: Param1, param2: Param2): Flow<Result> = execute(param1, param2)
        .flowOn(Dispatchers.IO)

    protected abstract fun execute(param1: Param1, param2: Param2): Flow<Result>
}