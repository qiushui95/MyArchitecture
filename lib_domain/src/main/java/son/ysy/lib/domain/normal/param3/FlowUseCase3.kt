package son.ysy.lib.domain.normal.param3

import kotlinx.coroutines.flow.Flow
import son.ysy.lib.entity.ModelResult

interface FlowUseCase3<Param1, Param2, Param3, Result> {
    operator fun invoke(param1: Param1, param2: Param2, param3: Param3): Flow<ModelResult<Result>>
}