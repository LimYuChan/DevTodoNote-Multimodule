package com.note.auth.data.dto

import com.note.core.domain.model.AuthInfo

internal fun AuthInfoDto.toAuthInfo(): AuthInfo {
    return AuthInfo(
        accessToken = access_token,
        tokenType = token_type,
        scope = scope
    )
}