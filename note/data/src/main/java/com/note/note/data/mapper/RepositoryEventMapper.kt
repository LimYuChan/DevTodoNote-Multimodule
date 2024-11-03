package com.note.note.data.mapper

import com.note.note.data.dto.RepositoryEventDto
import com.note.note.domain.model.RepositoryEvent

fun RepositoryEventDto.toModel(): RepositoryEvent {
    return RepositoryEvent(
        id = id,
        type = type,
        ref = payload?.ref,
        commitShas = payload?.commits?.mapNotNull { it.sha } ?: emptyList(),
        pullRequestMerged = payload?.pull_request?.merged ?: false,
        pullRequestHeadRef = payload?.pull_request?.head?.ref,
        pullRequestMergeCommitSha = payload?.pull_request?.merge_commit_sha
    )
}