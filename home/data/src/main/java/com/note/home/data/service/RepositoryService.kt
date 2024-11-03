package com.note.home.data.service

import com.note.home.data.dto.RepositoryDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface RepositoryService {

    @GET("user/repos")
    suspend fun getRepositories(
        @Query("page") page: Int,
        @Query("sort") sort: String = "updated"
    ): List<RepositoryDto>
}