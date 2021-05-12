package son.ysy.lib.domain.param3

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseFlowUseCase3Impl<Param1, Param2, Param3,
        Result> : FlowUseCase3<Param1, Param2, Param3, Result> {

    override fun invoke(
        param1: Param1,
        param2: Param2,
        param3: Param3,
    ): Flow<Result> = execute(param1, param2, param3)
        .flowOn(Dispatchers.IO)

    protected abstract fun execute(param1: Param1, param2: Param2, param3: Param3): Flow<Result>
}