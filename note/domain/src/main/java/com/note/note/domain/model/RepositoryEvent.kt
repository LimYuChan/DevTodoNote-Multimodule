package com.note.note.domain.model

data class RepositoryEvent(
    val id: Long,
    val type: String,
    val ref: String? = null,
    val commitShas: List<String> = emptyList(),
    val pullRequestMerged: Boolean = false,
    val pullRequestHeadRef: String? = null,
    val pullRequestMergeCommitSha: String? = null
)