package com.example.notemark.core.data.networking

import com.example.notemark.core.domain.util.NetworkError
import com.example.notemark.core.domain.util.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse


suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, NetworkError> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch(_: NoTransformationFoundException) {
                Result.Error(NetworkError.SERIALIZATION)
            }
        }
        400 -> Result.Error(NetworkError.BAD_REQUEST)
        401 -> Result.Error(NetworkError.UNAUTHORIZED)
        405 -> Result.Error(NetworkError.METHOD_NOT_ALLOWED)
        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        409 -> Result.Error(NetworkError.CONFLICT)
        429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
        else -> Result.Error(NetworkError.UNKNOWN)
    }
}