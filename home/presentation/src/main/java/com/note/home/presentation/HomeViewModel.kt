package com.note.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.note.home.domain.HomeRepository
import com.note.home.domain.model.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    homeRepository: HomeRepository
): ViewModel() {

    val repositories: Flow<PagingData<Repository>> =
        homeRepository.getRepositories().cachedIn(viewModelScope)

}