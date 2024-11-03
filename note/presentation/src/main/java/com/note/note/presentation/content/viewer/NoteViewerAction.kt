package com.note.note.presentation.content.viewer

sealed interface NoteViewerAction {
    data object DeleteNote: NoteViewerAction
    data object EditNote: NoteViewerAction
}