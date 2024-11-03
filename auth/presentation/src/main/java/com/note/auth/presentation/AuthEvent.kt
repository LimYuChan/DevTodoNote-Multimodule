package com.note.auth.presentation

import android.net.Uri
import com.note.core.ui.text.UiText

sealed interface AuthEvent {
    data class CreateLoginUri(val uri: Uri): AuthEvent
    data class Error(val error: UiText): AuthEvent
    data object LoginSuccess: AuthEvent
}