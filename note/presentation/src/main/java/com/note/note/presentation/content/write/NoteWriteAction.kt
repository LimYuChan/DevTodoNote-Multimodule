package com.note.note.presentation.content.write

import com.esafirm.imagepicker.model.Image
import com.note.note.domain.model.NoteImage
import com.note.note.presentation.content.model.NoteLinkUi

sealed interface NoteWriteAction {
    data class AddImages(val images: List<Image>): NoteWriteAction
    data class RemoveImage(val noteImage: NoteImage): NoteWriteAction

    data class AddLink(val link: String): NoteWriteAction
    data class RemoveLink(val link: NoteLinkUi): NoteWriteAction

    data class SetBranchName(val name: String): NoteWriteAction
    data object ResetBranchName: NoteWriteAction

    data object Submit: NoteWriteAction
}