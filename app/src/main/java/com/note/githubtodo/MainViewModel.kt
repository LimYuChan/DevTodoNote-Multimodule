package com.note.githubtodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.note.core.domain.AuthStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authStorage: AuthStorage
) : ViewModel() {

    private val eventChannel = Channel<MainEvent>()
    val event = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            delay(SPLASH_DURATION)
            eventChannel.send(MainEvent.LoadFinish(isLoggedIn = authStorage.get()?.isValid() == true))
        }
    }

    companion object {
        private const val SPLASH_DURATION = 1_000L
    }
}