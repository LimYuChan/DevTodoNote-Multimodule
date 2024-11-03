package com.note.note.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.note.note.domain.enums.NoteState
import com.note.note.domain.model.Note
import com.note.note.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteRepository: NoteRepository
): ViewModel() {

    fun getNotes(repositoryId: Int, state: NoteState?): Flow<PagingData<Note>> {
        return if(state == null) {
            noteRepository.getNotesByRepositoryId(repositoryId).cachedIn(viewModelScope)
        }else{
            noteRepository.getNotesByState(repositoryId, state).cachedIn(viewModelScope)
        }
    }

}