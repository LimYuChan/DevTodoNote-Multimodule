package com.note.note.data

import com.note.note.data.dto.RepositoryEventDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepositoryEventService {
    @GET("/repos/{owner}/{repo}/events")
    suspend fun getUserRepositoryEvents(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1
    ): Response<List<RepositoryEventDto>>
}