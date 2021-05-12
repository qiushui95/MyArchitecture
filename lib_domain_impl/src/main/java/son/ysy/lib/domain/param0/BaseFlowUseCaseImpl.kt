package son.ysy.lib.domain.param0

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseFlowUseCaseImpl<Result> : FlowUseCase<Result> {

    override fun invoke(): Flow<Result> =execute()
        .flowOn(Dispatchers.IO)

    protected abstract fun execute(): Flow<Result>
}