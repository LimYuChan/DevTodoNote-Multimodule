package com.note.home.domain

import androidx.paging.PagingData
import com.note.home.domain.model.Repository
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getRepositories(): Flow<PagingData<Repository>>
}