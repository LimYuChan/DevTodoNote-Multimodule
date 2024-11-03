package com.note.auth.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.note.auth.domain.AuthRepository
import com.note.core.common.Logg
import com.note.core.domain.result.Result
import com.note.core.ui.text.UiText
import com.note.core.ui.text.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    private var loginStateKey = ""

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    private val _event = Channel<AuthEvent>()
    val event = _event.receiveAsFlow()

    fun onAction() {

    }

    fun onClickLogin() {
        _state.value = state.value.copy(
            isLoggingIn = true
        )
        val uri = Uri.Builder()
            .scheme("https")
            .authority(BuildConfig.AUTH_HOST)
            .appendPath("login")
            .appendPath("oauth")
            .appendPath("authorize")
            .appendQueryParameter(AUTH_RESULT_STATE_KEY, getRandomString(STATE_KEY_LENGTH))
            .appendQueryParameter("scope", "repo, read:user")
            .appendQueryParameter("client_id", BuildConfig.GITHUB_CLIENT_ID)
            .build()

        viewModelScope.launch {
            _event.send(AuthEvent.CreateLoginUri(uri))
        }
    }

    private fun getRandomString(length: Int): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        loginStateKey = (1..length)
            .map { charset.random() }
            .joinToString("")
        return loginStateKey
    }

    fun getAccessToken(stateKey: String?, code: String?) {
        if(stateKey != loginStateKey || code == null){
            viewModelScope.launch {
                _event.send(AuthEvent.Error(UiText.StringResource(R.string.error_message_not_match_statekey)))
            }
            return
        }
        viewModelScope.launch {
            val result = repository.getAccessToken(code)
            when (result) {
                is Result.Success -> {
                    _event.send(AuthEvent.LoginSuccess)
                }

                is Result.Error -> {
                    _event.send(AuthEvent.Error(result.error.asUiText()))
                }
            }
            _state.value = state.value.copy(isLoggingIn = false)
        }
    }


    companion object {
        const val AUTH_RESULT_STATE_KEY = "state"
        private const val STATE_KEY_LENGTH = 32
    }
}