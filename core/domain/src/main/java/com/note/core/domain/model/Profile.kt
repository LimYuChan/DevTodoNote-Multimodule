package com.note.core.domain.model

data class Profile(
    val id: Int = 0,
    val email: String = "",
    val login: String = "",
    val publicRepos: Int = 0,
    val totalPrivateRepos: Int = 0,
    val ownedPrivateRepos: Int = 0,
    val avatarUrl: String = "",
    val htmlUrl: String = "",
)
