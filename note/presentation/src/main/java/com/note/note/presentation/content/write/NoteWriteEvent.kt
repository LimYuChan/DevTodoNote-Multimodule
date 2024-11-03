package com.note.note.presentation.content.write

import com.note.core.ui.text.UiText

interface NoteWriteEvent {
    data class Error(val error: UiText): NoteWriteEvent
    data object SavedComplete: NoteWriteEvent
}