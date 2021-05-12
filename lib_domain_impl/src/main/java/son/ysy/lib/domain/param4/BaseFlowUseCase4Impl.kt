package son.ysy.lib.domain.param4

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseFlowUseCase4Impl<Param1, Param2, Param3, Param4, Result> :
    FlowUseCase4<Param1, Param2, Param3, Param4, Result> {

    override fun invoke(
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4
    ): Flow<Result> = execute(param1, param2, param3, param4)
        .flowOn(Dispatchers.IO)

    protected abstract fun execute(
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4
    ): Flow<Result>
}