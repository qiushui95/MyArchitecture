package son.ysy.lib.domain.normal.param0

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import son.ysy.lib.domain.DomainResult

abstract class BaseFlowUseCaseImpl<Result> : FlowUseCase<Result> {

    override fun invoke(): Flow<DomainResult<Result>> = executeFlow()
        .flowOn(Dispatchers.IO)
        .map {
            DomainResult.build(it)
        }

    protected open fun executeFlow() = flow {
        emit(execute())
    }

    protected abstract suspend fun execute(): Result?
}