package com.note.core.domain.model

data class AuthInfo(
    val accessToken: String,
    val tokenType: String,
    val scope: String
) {
    fun isValid(): Boolean {
        return accessToken.isNotEmpty() && tokenType.isNotEmpty() && scope.isNotEmpty()
    }
}
