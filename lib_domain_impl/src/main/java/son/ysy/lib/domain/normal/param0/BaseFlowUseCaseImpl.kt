package son.ysy.lib.domain.normal.param0

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import son.ysy.lib.entity.ModelResult

abstract class BaseFlowUseCaseImpl<Result> : FlowUseCase<Result> {

    override fun invoke(): Flow<ModelResult<Result>> = execute()
        .flowOn(Dispatchers.IO)

    protected abstract fun execute(): Flow<ModelResult<Result>>
}