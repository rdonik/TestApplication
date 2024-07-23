package test.application.grafic.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class UseCase<in P, T>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: P): T {
        return withContext(coroutineDispatcher) {
            performAction(parameters)
        }
    }

    protected abstract suspend fun performAction(parameters: P): T
}