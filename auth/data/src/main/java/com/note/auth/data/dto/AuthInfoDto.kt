package com.note.auth.data.dto

data class AuthInfoDto(
    val access_token: String,
    val token_type: String,
    val scope: String
)
