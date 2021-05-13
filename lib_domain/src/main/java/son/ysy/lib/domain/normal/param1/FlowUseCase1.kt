package son.ysy.lib.domain.normal.param1

import kotlinx.coroutines.flow.Flow
import son.ysy.lib.domain.DomainResult

interface FlowUseCase1<Param, Result> {
    operator fun invoke(param: Param): Flow<DomainResult<Result>>
}