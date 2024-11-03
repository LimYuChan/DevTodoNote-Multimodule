package com.note.core.domain

import com.note.core.domain.model.AuthInfo

interface AuthStorage {
    fun get(): AuthInfo?
    suspend fun set(authInfo: AuthInfo?)
}