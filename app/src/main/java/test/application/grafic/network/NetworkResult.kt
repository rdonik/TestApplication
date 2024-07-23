package test.application.grafic.network

sealed class NetworkResult<out T>

data class ErrorApiResult<out T>(
    var message: String,
    val throwable: Throwable? = null,
    var code: Int = 0
) : NetworkResult<T>()

data class SuccessApiResult<out T>(
    val data: T
) : NetworkResult<T>()