package com.note.note.presentation.content.adapter.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.note.core.common.Logg
import com.note.note.domain.model.NoteImage
import com.note.note.presentation.databinding.ItemNoteImageBinding
import com.note.note.presentation.content.adapter.NoteImageAdapter.ImageItemAction

class NoteImageViewHolder(
    private val binding: ItemNoteImageBinding,
    private val isDeleteAble: Boolean,
    private val action: (ImageItemAction) -> Unit
): ViewHolder(binding.root) {

    init {
        with(binding) {
            icRemove.setOnClickListener {
                if(isDeleteAble) {
                    noteImage?.let { item -> action(ImageItemAction.Delete(item)) }
                }
            }

            icView.setOnClickListener {
                noteImage?.let { item -> action(ImageItemAction.OpenViewer(item.filePath)) }
            }
        }
    }

    fun bind(data: NoteImage) {
        with(binding) {
            noteImage = data

            icRemove.isVisible = isDeleteAble

            Glide.with(image)
                .load(data.filePath)
                .into(image)
        }
    }
}