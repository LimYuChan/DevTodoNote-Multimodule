package com.note.core.navigator

sealed interface RouteEvent {
    data object NavigateUp: RouteEvent
    data object AuthToHome: RouteEvent
    data object Unauthorized: RouteEvent
    data class HomeToTask(val repositoryId: Int, val repositoryName: String) : RouteEvent
}