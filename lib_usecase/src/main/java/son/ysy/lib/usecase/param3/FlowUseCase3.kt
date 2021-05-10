package son.ysy.lib.usecase.param3

import kotlinx.coroutines.flow.Flow

interface FlowUseCase3<Param1, Param2, Param3, Result> {
    operator fun invoke(param1: Param1, param2: Param2, param3: Param3): Flow<Result>
}