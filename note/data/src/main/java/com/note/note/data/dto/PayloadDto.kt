package com.note.note.data.dto

data class PayloadDto(
    val ref: String? = null,
    val commits: List<CommitDto>? = emptyList(),
    val pull_request: PullRequestDto? = null
)
