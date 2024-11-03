package com.note.core.navigator

import com.note.core.common.NoteDispatcher
import com.note.core.common.annotation.Dispatcher
import com.note.core.common.annotation.MainScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor(
    @MainScope
    private val mainScope: CoroutineScope
){
    private val routeEventChannel = Channel<RouteEvent>()
    val routeEvent = routeEventChannel.receiveAsFlow()

    fun navigate(routeEvent: RouteEvent) {
        launchEvent(routeEvent)
    }

    fun navigateUp() {
        launchEvent(RouteEvent.NavigateUp)
    }

    private fun launchEvent(routeEvent: RouteEvent) {
        mainScope.launch {
            routeEventChannel.send(routeEvent)
        }
    }
}