package com.note.core.data.user

import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET("/user")
    suspend fun getProfile(): Response<ProfileDto>
}