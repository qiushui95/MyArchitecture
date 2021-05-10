package son.ysy.lib.usecase.param1

import kotlinx.coroutines.flow.Flow

interface FlowUseCase1<Param, Result> {
    operator fun invoke(param: Param): Flow<Result>
}