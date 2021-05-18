package son.ysy.lib.domain.normal.param2

import kotlinx.coroutines.flow.Flow
import son.ysy.lib.domain.DomainResult

interface FlowUseCase2<Param1, Param2, Result> {
    operator fun invoke(param1: Param1, param2: Param2): Flow<DomainResult<Result>>
}