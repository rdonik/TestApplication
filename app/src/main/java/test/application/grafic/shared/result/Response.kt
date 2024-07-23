package test.application.grafic.shared.result

import retrofit2.HttpException
import retrofit2.Response
import test.application.grafic.network.ErrorApiResult
import test.application.grafic.network.NetworkResult
import test.application.grafic.network.SuccessApiResult
import java.net.SocketTimeoutException

suspend fun <T : Any> getResult(data: suspend () -> Response<T>): NetworkResult<T> {
    return try {
        val response = data()
        if (response.isSuccessful) {
            SuccessApiResult(response.body()!!)
        } else {
            val error = parseError(response)
            ErrorApiResult(
                code = error.first,
                message = error.second
            )
        }
    } catch (e: Exception) {
        handleException(e)
    }
}

fun <T : Any> handleException(e: Exception): ErrorApiResult<T> {
    return when (e) {
        is HttpException -> {
            val error = parseError(e.response()!!)
            ErrorApiResult(
                code = error.first,
                message = error.second
            )
        }

        is SocketTimeoutException -> ErrorApiResult(
            message = getErrorMessage(-1, e.message)
        )

        else -> ErrorApiResult(
            message = getErrorMessage(Int.MAX_VALUE, e.message)
        )
    }
}

fun parseError(response: Response<*>): Pair<Int, String> {
    return try {
        Pair(response.code(), response.errorBody()!!.string())
    } catch (e: Exception) {
        Pair(response.code(), getErrorMessage(response.code()))
    }
}

private fun getErrorMessage(code: Int, message: String? = null): String {
    return when (code) {
        -1 -> "Timeout"
        400 -> "Bad request"
        401 -> "Unauthorised"
        404 -> "Not found"
        500 -> "Ошибка в сервере"
        502 -> "Технические неполадки"
        else -> "Message: $message"
    }
}