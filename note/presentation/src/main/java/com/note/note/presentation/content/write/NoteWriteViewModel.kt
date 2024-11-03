package com.note.note.presentation.content.write

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.esafirm.imagepicker.model.Image
import com.note.core.common.Logg
import com.note.core.domain.result.DataError
import com.note.core.domain.result.Result
import com.note.core.domain.util.IntegerUtils
import com.note.core.ui.text.UiText
import com.note.core.ui.text.asUiText
import com.note.note.domain.model.Note
import com.note.note.domain.model.NoteImage
import com.note.note.domain.model.NoteLink
import com.note.note.domain.repository.LinkMetaDataFetcher
import com.note.note.domain.repository.NoteRepository
import com.note.note.presentation.R
import com.note.note.presentation.content.model.NoteLinkUi
import com.note.note.presentation.content.write.util.WriteMode
import com.note.note.presentation.content.write.util.toNoteImage
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Locale


class NoteWriteViewModel @AssistedInject constructor(
    private val noteRepository: NoteRepository,
    private val linkMetaDataFetcher: LinkMetaDataFetcher,
    @Assisted private val savedStateHandle: SavedStateHandle,
    @Assisted private val noteId: Long,
    @Assisted private val repositoryId: Int
) : ViewModel() {

    private val eventChannel = Channel<NoteWriteEvent>()
    val event = eventChannel.receiveAsFlow()

    val state = savedStateHandle.getStateFlow(KEY_STATE, NoteWriteState())

    val contentText = MutableStateFlow(savedStateHandle[KEY_CONTENT_TEXT] ?: "")

    private val savedNoteId = savedStateHandle.get<Long>(KEY_NOTE_ID) ?: INVALID_NOTE_ID
    private val savedRepositoryId =
        savedStateHandle.get<Int>(KEY_REPOSITORY_ID) ?: INVALID_REPOSITORY_ID

    init {
        contentText.onEach { newText ->
            savedStateHandle[KEY_CONTENT_TEXT] = newText
        }.launchIn(viewModelScope)

        savedStateHandle[KEY_NOTE_ID] = noteId
        savedStateHandle[KEY_REPOSITORY_ID] = repositoryId

        setupNote()
    }

    private fun setupNote() {
        updateState {
            copy(content = content.copy(repositoryId = savedRepositoryId))
        }

        if (savedNoteId == INVALID_NOTE_ID) {
            generateAndSetBranchName()
        } else {
            getNote(savedNoteId)
        }
    }

    private fun generateAndSetBranchName() {
        viewModelScope.launch {
            val branchNumber = getLastContentIdOrRandom()
            val branchName = String.format(Locale.getDefault(), BRANCH_FORMAT, branchNumber)
            updateBranchName(branchName)
        }
    }

    private suspend fun getLastContentIdOrRandom(): Long {
        return when (val result = noteRepository.getLastContentId()) {
            is Result.Success -> result.data ?: IntegerUtils.randomToRange().toLong()
            is Result.Error -> IntegerUtils.randomToRange().toLong()
        }
    }


    private fun updateBranchName(branchName: String) {
        updateState {
            copy(content = content.copy(branchName = branchName))
        }
    }

    private fun getNote(id: Long) {
        viewModelScope.launch {
            setLoadingState(true)
            val result = noteRepository.getNote(id)
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
                    setInvalidNoteId()
                    sendErrorEvent(UiText.StringResource(R.string.note_not_found))
                }
            }

            is Result.Error -> {
                setInvalidNoteId()
                sendErrorEvent(result.error.asUiText())
            }
        }
    }

    private fun initNoteData(note: Note) {
        updateState {
            copy(
                writeMode = WriteMode.EDIT,
                content = note.content,
                images = note.images,
                links = note.links.mapIndexed { index, noteLink ->
                    NoteLinkUi(isLoading = false, linkIndex = index, link = noteLink)
                }
            )
        }

        contentText.value = note.content.content
    }

    fun onAction(action: NoteWriteAction) {
        when (action) {
            is NoteWriteAction.AddImages -> addImages(action.images)
            is NoteWriteAction.RemoveImage -> removeImage(action.noteImage)
            is NoteWriteAction.AddLink -> addLink(action.link)
            is NoteWriteAction.RemoveLink -> removeLink(action.link)
            is NoteWriteAction.SetBranchName -> updateBranchName(action.name)
            is NoteWriteAction.ResetBranchName -> generateAndSetBranchName()
            is NoteWriteAction.Submit -> submit()
        }
    }

    private fun addImages(images: List<Image>) {
        updateImages {
            this + images.map { it.toNoteImage() }
        }
    }

    private fun removeImage(noteImage: NoteImage) {
        updateImages {
            filterNot { image -> image == noteImage }
        }
    }

    private fun addLink(link: String) {
        val newLinkUi = NoteLinkUi(isLoading = true, link = NoteLink())
        updateLinks {
            this + newLinkUi
        }

        viewModelScope.launch {
            when (val result = linkMetaDataFetcher.fetch(link)) {
                is Result.Success -> {
                    val updatedLinks = state.value.links.toMutableList()
                    val lastIndex = updatedLinks.lastIndex
                    updatedLinks[lastIndex] = updatedLinks[lastIndex].copy(
                        isLoading = false,
                        linkIndex = lastIndex,
                        link = result.data
                    )
                    updateLinks {
                        updatedLinks
                    }
                }

                is Result.Error -> {
                    updateLinks {
                        dropLast(1)
                    }
                    sendErrorEvent(result.error.asUiText())
                }
            }
        }
    }


    private fun removeLink(linkUi: NoteLinkUi) {
        updateLinks {
            filterNot { link -> link == linkUi }
        }
    }

    private fun submit() {
        viewModelScope.launch {
            setLoadingState(true)
            val note = Note(
                content = state.value.content.copy(content = contentText.value),
                images = state.value.images,
                links = state.value.links.map { it.link }
            )
            when (val result = noteRepository.upsertNote(note)) {
                is Result.Success -> eventChannel.send(NoteWriteEvent.SavedComplete)
                is Result.Error -> sendErrorEvent(result.error.asUiText())
            }
            setLoadingState(false)
        }
    }

    private fun updateState(update: NoteWriteState.() -> NoteWriteState) {
        savedStateHandle[KEY_STATE] = state.value.update()
    }

    private fun updateImages(update: List<NoteImage>.() -> List<NoteImage>) {
        updateState {
            copy(images = images.update())
        }
    }

    private fun updateLinks(update: List<NoteLinkUi>.() -> List<NoteLinkUi>) {
        updateState {
            copy(links = links.update())
        }
    }

    private fun setInvalidNoteId() {
        savedStateHandle[KEY_NOTE_ID] = INVALID_NOTE_ID
    }

    private fun setLoadingState(isLoading: Boolean) {
        updateState {
            copy(isLoading = isLoading)
        }
    }

    private suspend fun sendErrorEvent(error: UiText) {
        eventChannel.send(NoteWriteEvent.Error(error))
    }

    @AssistedFactory
    interface Factory {
        fun create(
            savedStateHandle: SavedStateHandle,
            noteId: Long,
            repositoryId: Int
        ): NoteWriteViewModel
    }


    companion object {
        fun provideFactory(
            factory: Factory,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
            noteId: Long,
            repositoryId: Int
        ): AbstractSavedStateViewModelFactory {
            return object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return factory.create(handle, noteId, repositoryId) as T
                }
            }
        }

        private const val INVALID_NOTE_ID: Long = -1L
        private const val INVALID_REPOSITORY_ID: Int = -1
        private const val BRANCH_FORMAT: String = "WORK-%d"
        private const val KEY_NOTE_ID: String = "noteId"
        private const val KEY_STATE: String = "state"
        private const val KEY_CONTENT_TEXT: String = "contentText"

        private const val KEY_REPOSITORY_ID: String = "repositoryId"
    }
}
