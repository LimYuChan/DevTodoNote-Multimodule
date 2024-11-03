package com.note.auth.data

import com.note.auth.data.dto.toAuthInfo
import com.note.auth.domain.AuthRepository
import com.note.core.common.NoteDispatcher
import com.note.core.common.annotation.Dispatcher
import com.note.core.data.network.safeCall
import com.note.core.domain.AuthStorage
import com.note.core.domain.result.DataError
import com.note.core.domain.result.EmptyResult
import com.note.core.domain.result.Result
import com.note.core.domain.result.asEmptyDataResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val service: AuthService,
    private val authStorage: AuthStorage,
    @Dispatcher(NoteDispatcher.IO) private val ioDispatcher: CoroutineDispatcher
): AuthRepository {

    override suspend fun getAccessToken(code: String): EmptyResult<DataError.Network> {
        return withContext(ioDispatcher) {
            val result = safeCall { service.getAccessToken(code = code) }

            when(result) {
                is Result.Success -> {
                    val authInfo = result.data.toAuthInfo()
                    authStorage.set(authInfo)

                    Result.Success(Unit)
                }
                is Result.Error -> {
                    result.asEmptyDataResult()
                }
            }
        }
    }
}