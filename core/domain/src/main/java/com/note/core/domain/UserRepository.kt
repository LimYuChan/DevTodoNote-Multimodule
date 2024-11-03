package com.note.core.domain

import com.note.core.domain.model.Profile
import com.note.core.domain.result.DataError
import com.note.core.domain.result.Result

interface UserRepository {
    suspend fun getProfile(): Result<Profile, DataError.Network>
}