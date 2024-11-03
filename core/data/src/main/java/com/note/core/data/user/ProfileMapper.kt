package com.note.core.data.user

import com.note.core.domain.model.Profile

internal fun ProfileDto.toProfile(): Profile {
    return Profile(
        id = id,
        email = email ?: "",
        login = login ?: "",
        publicRepos = public_repos,
        totalPrivateRepos = total_private_repos,
        ownedPrivateRepos = owned_private_repos,
        avatarUrl = avatar_url,
        htmlUrl = html_url
    )
}