package com.example.notemark.core.presentation.util

import android.content.Context
import com.example.notemark.R
import com.example.notemark.core.domain.util.DataError

fun DataError.toString(context: Context): String {
    val resId = when (this) {
        DataError.REQUEST_TIMEOUT -> R.string.request_timeout
        DataError.TOO_MANY_REQUESTS -> R.string.too_many_requests
        DataError.NO_INTERNET -> R.string.no_internet
        DataError.SERVER_ERROR -> R.string.server_error
        DataError.SERIALIZATION -> R.string.serialization
        DataError.CONFLICT -> R.string.conflict
        DataError.METHOD_NOT_ALLOWED -> R.string.method_not_allowed
        DataError.UNAUTHORIZED -> R.string.unauthorized
        DataError.BAD_REQUEST -> R.string.bad_request
        DataError.UNKNOWN -> R.string.unknown
        DataError.DATABASE_ERROR -> R.string.database_error
    }
    return context.getString(resId)
}