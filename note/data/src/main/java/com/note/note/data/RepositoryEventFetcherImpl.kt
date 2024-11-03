package com.note.note.data

import com.note.core.common.NoteDispatcher
import com.note.core.common.annotation.Dispatcher
import com.note.core.data.network.safeCall
import com.note.core.data.user.UserService
import com.note.core.database.dao.NoteContentDao
import com.note.core.domain.result.DataError
import com.note.core.domain.result.Result
import com.note.note.data.dto.RepositoryEventDto
import com.note.note.data.mapper.toModel
import com.note.note.domain.enums.BranchState
import com.note.note.domain.repository.RepositoryEventFetcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryEventFetcherImpl @Inject constructor(
    private val service: RepositoryEventService,
    private val userService: UserService,
    @Dispatcher(NoteDispatcher.IO) private val ioDispatcher: CoroutineDispatcher
) : RepositoryEventFetcher {

    override suspend fun fetch(repositoryName: String, branchName: String): Result<BranchState, DataError.Network> {
        return when (val ownerResult = getRepositoryOwner()) {
            is Result.Success -> {
                when (val eventResult = getRepositoryEvents(ownerResult.data, repositoryName, branchName)) {
                    is Result.Success -> Result.Success(eventResult.data)
                    is Result.Error -> eventResult
                }
            }
            is Result.Error -> ownerResult
        }
    }

    private suspend fun getRepositoryOwner(): Result<String, DataError.Network> {
        return withContext(ioDispatcher) {
            val result = safeCall { userService.getProfile() }
            when (result) {
                is Result.Success -> {
                    val owner = result.data.login
                    if (owner.isNullOrBlank()) {
                        Result.Error(DataError.Network.NOT_FOUND)
                    } else {
                        Result.Success(owner)
                    }
                }
                is Result.Error -> result
            }
        }
    }

    private suspend fun getRepositoryEvents(owner: String, repositoryName: String, branchName: String): Result<BranchState, DataError.Network> {
        return withContext(ioDispatcher) {
            val result = safeCall { service.getUserRepositoryEvents(owner, repositoryName) }
            when (result) {
                is Result.Success -> {
                    val branchState = fetchBranchState(result.data, branchName)
                    Result.Success(branchState)
                }
                is Result.Error -> result
            }
        }
    }

    private fun fetchBranchState(events: List<RepositoryEventDto>, branchName: String): BranchState {
        val mappedEvents = events.map { it.toModel() }

        val branchEvents = mappedEvents.filter {
            it.ref.equals(branchName, ignoreCase = true) || it.pullRequestHeadRef.equals(branchName, ignoreCase = true)
        }

        return when {
            branchEvents.isEmpty() -> BranchState.NONE
            branchEvents.any { it.pullRequestMerged } -> BranchState.MERGE
            else -> BranchState.COMMIT
        }
    }
}
