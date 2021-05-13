package son.ysy.lib.domain.normal.param1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import son.ysy.lib.entity.ModelResult

abstract class BaseFlowUseCase1Impl<Param, Result> : FlowUseCase1<Param, Result> {

    override fun invoke(param: Param): Flow<ModelResult<Result>> = execute(param)
        .flowOn(Dispatchers.IO)

    protected abstract fun execute(param: Param): Flow<ModelResult<Result>>
}