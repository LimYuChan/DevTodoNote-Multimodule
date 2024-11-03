package com.note.note.presentation.content.viewer

import com.note.core.ui.text.UiText

sealed interface NoteViewerEvent {
    data class Error(val error: UiText): NoteViewerEvent
    data class Edit(val noteId: Long, val repositoryId: Int): NoteViewerEvent
    data object DeletedComplete: NoteViewerEvent
}