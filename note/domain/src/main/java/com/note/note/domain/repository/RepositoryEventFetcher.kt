package com.note.note.domain.repository

import com.note.core.domain.result.DataError
import com.note.core.domain.result.Result
import com.note.note.domain.enums.BranchState

interface RepositoryEventFetcher {
    suspend fun fetch(repositoryName: String, branchName: String): Result<BranchState, DataError.Network>
}