package son.ysy.lib.domain.normal.param1

import kotlinx.coroutines.flow.Flow
import son.ysy.lib.entity.ModelResult

interface FlowUseCase1<Param, Result> {
    operator fun invoke(param: Param): Flow<ModelResult<Result>>
}