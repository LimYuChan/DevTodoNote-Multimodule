package com.note.auth.domain

import com.note.core.domain.result.DataError
import com.note.core.domain.result.EmptyResult

interface AuthRepository {
    suspend fun getAccessToken(code: String): EmptyResult<DataError.Network>
}