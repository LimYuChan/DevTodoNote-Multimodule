package com.note.githubtodo

sealed interface MainEvent {
    data class LoadFinish(val isLoggedIn: Boolean) : MainEvent
}