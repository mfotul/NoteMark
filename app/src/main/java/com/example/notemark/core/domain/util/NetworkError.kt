package com.example.notemark.core.domain.util

enum class NetworkError: Error {
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
}