package com.note.core.data.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.note.core.common.NoteDispatcher
import com.note.core.common.annotation.Dispatcher
import com.note.core.domain.AuthStorage
import com.note.core.domain.model.AuthInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @Dispatcher(NoteDispatcher.IO) private val ioDispatcher: CoroutineDispatcher
): AuthStorage {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val TOKEN_TYPE_KEY = stringPreferencesKey("token_type")
        private val SCOPE_KEY = stringPreferencesKey("scope")
    }

    override fun get(): AuthInfo? {
        return runBlocking(ioDispatcher) {
            dataStore.data
                .map { preferences ->
                    val accessToken = preferences[ACCESS_TOKEN_KEY]
                    val tokenType = preferences[TOKEN_TYPE_KEY]
                    val scope = preferences[SCOPE_KEY]

                    if (!accessToken.isNullOrEmpty() && !tokenType.isNullOrEmpty() && !scope.isNullOrEmpty()) {
                        AuthInfo(accessToken, tokenType, scope)
                    } else {
                        null
                    }
                }
                .firstOrNull()
        }
    }

    override suspend fun set(authInfo: AuthInfo?) {
        withContext(ioDispatcher) {
            dataStore.edit { preferences ->
                preferences[ACCESS_TOKEN_KEY] = authInfo?.accessToken ?: ""
                preferences[TOKEN_TYPE_KEY] = authInfo?.tokenType ?: ""
                preferences[SCOPE_KEY] = authInfo?.scope ?: ""
            }
        }
    }
}