package com.example.notemark.core.presentation.util

import android.content.Context
import com.example.notemark.R
import com.example.notemark.core.domain.util.NetworkError

fun NetworkError.toString(context: Context): String {
    val resId = when (this) {
        NetworkError.REQUEST_TIMEOUT -> R.string.request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.too_many_requests
        NetworkError.NO_INTERNET -> R.string.no_internet
        NetworkError.SERVER_ERROR -> R.string.server_error
        NetworkError.SERIALIZATION -> R.string.serialization
        NetworkError.CONFLICT -> R.string.conflict
        NetworkError.METHOD_NOT_ALLOWED -> R.string.method_not_allowed
        NetworkError.UNAUTHORIZED -> R.string.unauthorized
        NetworkError.BAD_REQUEST -> R.string.bad_request
        NetworkError.UNKNOWN -> R.string.unknown
    }
    return context.getString(resId)
}