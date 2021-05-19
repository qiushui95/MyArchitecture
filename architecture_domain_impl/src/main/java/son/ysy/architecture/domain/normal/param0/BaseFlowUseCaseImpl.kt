package son.ysy.architecture.domain.normal.param0

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import son.ysy.architecture.domain.DomainResult
import son.ysy.architecture.domain.build

abstract class BaseFlowUseCaseImpl<Result> : FlowUseCase<Result> {

    override fun invoke(): Flow<DomainResult<Result>> = executeFlow()
        .map {
            DomainResult.build(it)
        }

    protected open fun executeFlow() = flow {
        emit(execute())
    }

    protected abstract suspend fun execute(): Result?
}