package son.ysy.architecture.domain.normal.param0

import kotlinx.coroutines.flow.Flow
import son.ysy.architecture.domain.DomainResult

interface FlowUseCase<Result> {
    operator fun invoke(): Flow<DomainResult<Result>>
}