package son.ysy.lib.domain.normal.param0

import kotlinx.coroutines.flow.Flow
import son.ysy.lib.domain.DomainResult

interface FlowUseCase<Result> {
    operator fun invoke(): Flow<DomainResult<Result>>
}