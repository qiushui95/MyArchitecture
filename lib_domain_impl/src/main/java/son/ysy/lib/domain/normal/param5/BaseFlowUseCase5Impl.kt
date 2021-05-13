package son.ysy.lib.domain.normal.param5

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import son.ysy.lib.entity.ModelResult

abstract class BaseFlowUseCase5Impl<Param1, Param2, Param3, Param4, Param5,
        Result> : FlowUseCase5<Param1, Param2, Param3, Param4, Param5, Result> {

    override fun invoke(
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4,
        param5: Param5
    ): Flow<ModelResult<Result>> = execute(param1, param2, param3, param4, param5)
        .flowOn(Dispatchers.IO)

    protected abstract fun execute(
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4,
        param5: Param5
    ): Flow<ModelResult<Result>>
}