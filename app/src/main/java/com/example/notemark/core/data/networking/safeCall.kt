package com.example.notemark.core.data.networking

import com.example.notemark.core.domain.util.DataError
import com.example.notemark.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, DataError> {
    val response = try {
        execute()
    } catch(_: UnresolvedAddressException) {
        return Result.Error(DataError.NO_INTERNET)
    } catch(_: SerializationException) {
        return Result.Error(DataError.SERIALIZATION)
    } catch(_: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(DataError.UNKNOWN)
    }
    return responseToResult(response)
}