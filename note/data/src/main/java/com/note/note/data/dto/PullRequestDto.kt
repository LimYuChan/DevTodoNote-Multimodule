package com.note.note.data.dto

data class PullRequestDto(
    val merged: Boolean? = false,
    val head: HeadDto? = null,
    val merge_commit_sha: String? = null
)
