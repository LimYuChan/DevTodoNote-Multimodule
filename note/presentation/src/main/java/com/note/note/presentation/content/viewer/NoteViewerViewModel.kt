package com.note.note.presentation.content.viewer

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.note.core.common.Logg
import com.note.core.domain.result.DataError
import com.note.core.domain.result.Result
import com.note.core.ui.text.UiText
import com.note.core.ui.text.asUiText
import com.note.note.domain.model.Note
import com.note.note.domain.repository.NoteRepository
import com.note.note.domain.repository.RepositoryEventFetcher
import com.note.note.presentation.R
import com.note.note.presentation.content.model.NoteLinkUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class NoteViewerViewModel @AssistedInject constructor(
    private val noteRepository: NoteRepository,
    private val repositoryEventFetcher: RepositoryEventFetcher,
    @Assisted private val savedStateHandle: SavedStateHandle,
    @Assisted private val noteId: Long,
    @Assisted private val repositoryName: String
): ViewModel() {


    val state = savedStateHandle.getStateFlow(KEY_STATE, NoteViewerState())

    private val savedNoteId = savedStateHandle.get<Long>(KEY_NOTE_ID) ?: INVALID_NOTE_ID
    private val savedRepositoryName = savedStateHandle.get<String>(KEY_REPOSITORY_NAME) ?: ""

    private val eventChannel = Channel<NoteViewerEvent>()
    val event = eventChannel.receiveAsFlow()

    init {
        savedStateHandle[KEY_NOTE_ID] = noteId
        savedStateHandle[KEY_REPOSITORY_NAME] = repositoryName
    }


    fun getNote(){
        viewModelScope.launch {
            setLoadingState(true)
            val result = noteRepository.getNote(savedNoteId)
            handleNoteResult(result)
            setLoadingState(false)
        }
    }


    private suspend fun handleNoteResult(result: Result<Note?, DataError.Local>) {
        when (result) {
            is Result.Success -> {
                result.data?.let {
                    initNoteData(it)
                } ?: run {
                    sendErrorEvent(UiText.StringResource(R.string.note_not_found))
                }
            }

            is Result.Error -> {
                sendErrorEvent(result.error.asUiText())
            }
        }
    }

    private fun initNoteData(note: Note) {
        updateState {
            copy(
                content = note.content,
                images = note.images,
                links = note.links.mapIndexed { index, noteLink ->
                    NoteLinkUi(isLoading = false, linkIndex = index, link = noteLink)
                }
            )
        }
        getBranchState(note.content.branchName)
    }

    private fun getBranchState(branchName: String) {
        viewModelScope.launch {
            when(val result = repositoryEventFetcher.fetch(savedRepositoryName, branchName)) {
                is Result.Success -> {
                    updateState {
                        copy(content = content.copy(branchState = result.data))
                    }
                    noteRepository.updateBranchState(result.data, savedNoteId)
                }
                is Result.Error -> {
                    sendErrorEvent(result.error.asUiText())
                }
            }
        }
    }

    fun onAction(action: NoteViewerAction) {
       viewModelScope.launch {
           when(action) {
               is NoteViewerAction.DeleteNote -> {
                   when(val result = noteRepository.deleteNote(savedNoteId)) {
                       is Result.Success -> {
                           eventChannel.send(NoteViewerEvent.DeletedComplete)
                       }
                       is Result.Error -> {
                           sendErrorEvent(result.error.asUiText())
                       }
                   }
               }
               is NoteViewerAction.EditNote -> {
                   eventChannel.send(NoteViewerEvent.Edit(noteId = savedNoteId, repositoryId = state.value.content.repositoryId))
               }
           }
       }
    }

    private suspend fun sendErrorEvent(error: UiText) {
        eventChannel.send(NoteViewerEvent.Error(error))
    }

    private fun updateState(update: NoteViewerState.() -> NoteViewerState) {
        savedStateHandle[KEY_STATE] = state.value.update()
    }

    private fun setLoadingState(isLoading: Boolean) {
        updateState {
            copy(isLoading = isLoading)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            savedStateHandle: SavedStateHandle,
            noteId: Long,
            repositoryName: String
        ): NoteViewerViewModel
    }

    companion object {
        fun provideFactory(
            factory: Factory,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
            noteId: Long,
            repositoryName: String
        ): AbstractSavedStateViewModelFactory {
            return object: AbstractSavedStateViewModelFactory(owner, defaultArgs){
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return factory.create(handle, noteId, repositoryName) as T
                }
            }
        }

        private const val INVALID_NOTE_ID: Long = -1L
        private const val KEY_NOTE_ID: String = "noteId"
        private const val KEY_STATE: String = "state"
        private const val KEY_REPOSITORY_NAME = "repositoryName"
    }
}