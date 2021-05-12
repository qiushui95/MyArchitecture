package son.ysy.lib.domain.param0

import kotlinx.coroutines.flow.Flow

interface FlowUseCase<Result> {
    operator fun invoke(): Flow<Result>
}