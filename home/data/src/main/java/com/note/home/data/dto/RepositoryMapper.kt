package com.note.home.data.dto

import com.note.home.domain.model.Repository

internal fun RepositoryDto.toUserRepository(): Repository {
    return Repository(
        id = id,
        name = name,
        private = private,
        htmlUrl = html_url,
        description = description ?: "",
        updatedAt = updated_at ?: "",
        language = language ?: "",
        defaultBranch = default_branch
    )
}