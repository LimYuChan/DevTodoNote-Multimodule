package com.note.core.data.user

data class ProfileDto(
    val id: Int,
    val email: String? = null,
    val login: String? = null,
    val public_repos: Int,
    val total_private_repos: Int,
    val owned_private_repos: Int,
    val avatar_url: String,
    val html_url: String,
)
