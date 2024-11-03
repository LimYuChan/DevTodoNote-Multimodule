package com.note.auth.data

import com.note.auth.data.dto.AuthInfoDto
import com.note.core.data.BuildConfig
import com.note.core.data.network.NotAuthenticated
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface AuthService {
    @FormUrlEncoded
    @NotAuthenticated
    @POST("login/oauth/access_token")
    suspend fun getAccessToken(
        @Field("code") code: String,
        @Field("client_id") clientId: String = BuildConfig.GITHUB_CLIENT_ID,
        @Field("client_secret") clientSecret: String = BuildConfig.GITHUB_SECRET_KEY
    ): Response<AuthInfoDto>
}