package com.note.core.data.user

import com.note.core.common.NoteDispatcher
import com.note.core.common.annotation.Dispatcher
import com.note.core.data.network.safeCall
import com.note.core.domain.UserRepository
import com.note.core.domain.model.Profile
import com.note.core.domain.result.DataError
import com.note.core.domain.result.Result
import com.note.core.domain.result.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val service: UserService,
    @Dispatcher(NoteDispatcher.IO) private val ioDispatcher: CoroutineDispatcher
): UserRepository {

    override suspend fun getProfile(): Result<Profile, DataError.Network> {
        return withContext(ioDispatcher) {
            val result = safeCall { service.getProfile() }
            result.map { it.toProfile() }
        }
    }
}