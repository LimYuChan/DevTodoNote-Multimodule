package com.note.core.domain.result

sealed interface DataError: Error {

    enum class Network: DataError {
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERIALIZATION,
        PAYLOAD_TOO_LARGE,
        CONFLICT,
        SERVER_ERROR,
        NOT_FOUND_USER,
        UNKNOWN
    }

    enum class Local: DataError {
        DISK_FULL,
        SQL_EXCEPTION,
        UNKNOWN
    }

    enum class Parse: DataError {
        WRONG_URL_FORMAT,
        UNSUPPORTED_DATA_TYPE,
        DATA_PARSING_FAILED,
        UNKNOWN
    }
}