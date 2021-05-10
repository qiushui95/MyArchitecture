package son.ysy.lib.usecase.param0

import kotlinx.coroutines.flow.Flow

interface FlowUseCase<Result> {
    operator fun invoke(): Flow<Result>
}