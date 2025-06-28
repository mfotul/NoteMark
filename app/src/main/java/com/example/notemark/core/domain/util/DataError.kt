package com.example.notemark.core.domain.util

enum class DataError: Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER_ERROR,
    SERIALIZATION,
    CONFLICT,
    METHOD_NOT_ALLOWED,
    UNAUTHORIZED,
    BAD_REQUEST,
    UNKNOWN,
    DATABASE_ERROR,
}