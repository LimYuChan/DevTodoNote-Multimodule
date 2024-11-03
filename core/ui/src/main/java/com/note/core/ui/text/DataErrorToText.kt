package com.note.core.ui.text

import com.note.core.domain.result.DataError
import com.note.core.ui.R


fun DataError.asUiText(): UiText {
    return when(this) {
        DataError.Network.UNAUTHORIZED -> {
            UiText.StringResource(R.string.error_message_unauthorized)
        }
        DataError.Network.FORBIDDEN -> {
            UiText.StringResource(R.string.error_message_forbidden)
        }
        DataError.Network.NOT_FOUND -> {
            UiText.StringResource(R.string.error_message_not_found)
        }
        DataError.Network.TOO_MANY_REQUESTS -> {
            UiText.StringResource(R.string.error_message_too_many_requests)
        }
        DataError.Network.NO_INTERNET -> {
            UiText.StringResource(R.string.error_message_no_internet)
        }
        DataError.Network.PAYLOAD_TOO_LARGE -> {
            UiText.StringResource(R.string.error_message_payload_too_large)
        }
        DataError.Network.SERIALIZATION -> {
            UiText.StringResource(R.string.error_message_serialization)
        }
        DataError.Network.SERVER_ERROR -> {
            UiText.StringResource(R.string.error_message_server_error)
        }
        DataError.Network.NOT_FOUND_USER -> {
            UiText.StringResource(R.string.error_message_not_found_user)
        }
        DataError.Local.DISK_FULL -> {
            UiText.StringResource(R.string.error_message_disk_full)
        }
        DataError.Local.SQL_EXCEPTION -> {
            UiText.StringResource(R.string.error_message_sql_exception)
        }
        DataError.Parse.WRONG_URL_FORMAT -> {
            UiText.StringResource(R.string.error_message_wrong_url_format)
        }
        DataError.Parse.UNSUPPORTED_DATA_TYPE -> {
            UiText.StringResource(R.string.error_message_unsupported_data_type)
        }
        DataError.Parse.DATA_PARSING_FAILED -> {
            UiText.StringResource(R.string.error_message_data_parsing_failed)
        }
        else -> {
            UiText.StringResource(R.string.error_message_unknown)
        }
    }
}