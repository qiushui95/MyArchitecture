package son.ysy.lib.domain.normal.param0

import kotlinx.coroutines.flow.Flow
import son.ysy.lib.entity.ModelResult

interface FlowUseCase<Result> {
    operator fun invoke(): Flow<ModelResult<Result>>
}