package son.ysy.architecture.domain.normal.param3

import kotlinx.coroutines.flow.Flow
import son.ysy.architecture.domain.DomainResult

interface FlowUseCase3<Param1, Param2, Param3, Result> {
    operator fun invoke(param1: Param1, param2: Param2, param3: Param3): Flow<DomainResult<Result>>
}