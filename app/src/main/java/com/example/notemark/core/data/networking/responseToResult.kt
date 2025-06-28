package com.example.notemark.core.data.networking

import com.example.notemark.core.domain.util.DataError
import com.example.notemark.core.domain.util.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse


suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, DataError> {
    return when(response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch(_: NoTransformationFoundException) {
                Result.Error(DataError.SERIALIZATION)
            }
        }
        400 -> Result.Error(DataError.BAD_REQUEST)
        401 -> Result.Error(DataError.UNAUTHORIZED)
        405 -> Result.Error(DataError.METHOD_NOT_ALLOWED)
        408 -> Result.Error(DataError.REQUEST_TIMEOUT)
        409 -> Result.Error(DataError.CONFLICT)
        429 -> Result.Error(DataError.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.SERVER_ERROR)
        else -> Result.Error(DataError.UNKNOWN)
    }
}