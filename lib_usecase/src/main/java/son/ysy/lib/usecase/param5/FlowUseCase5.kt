package son.ysy.lib.usecase.param5

import kotlinx.coroutines.flow.Flow

interface FlowUseCase5<Param1, Param2, Param3, Param4, Param5, Result> {
    operator fun invoke(
        param1: Param1,
        param2: Param2,
        param3: Param3,
        param4: Param4,
        param5: Param5,
    ): Flow<Result>
}