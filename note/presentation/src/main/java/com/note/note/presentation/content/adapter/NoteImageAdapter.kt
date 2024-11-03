package com.note.note.presentation.content.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.note.note.domain.model.NoteImage
import com.note.note.presentation.R
import com.note.note.presentation.databinding.ItemNoteImageBinding
import com.note.note.presentation.content.adapter.viewholder.NoteImageViewHolder

class NoteImageAdapter(
    private val isDeleteAble: Boolean,
    private val action: (ImageItemAction) -> Unit
) : ListAdapter<NoteImage, NoteImageViewHolder>(diffUtil) {

    sealed interface ImageItemAction {
        data class OpenViewer(val filePath: String) : ImageItemAction
        data class Delete(val data: NoteImage) : ImageItemAction
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteImageViewHolder {
        val binding: ItemNoteImageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_note_image,
            parent,
            false
        )

        return NoteImageViewHolder(binding, isDeleteAble, action)
    }

    override fun onBindViewHolder(holder: NoteImageViewHolder, position: Int) {
        with(holder) {
            getItem(position)?.let {
                bind(it)
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<NoteImage>() {
            override fun areItemsTheSame(oldItem: NoteImage, newItem: NoteImage): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: NoteImage, newItem: NoteImage): Boolean {
                return oldItem.id == newItem.id && oldItem.filePath == newItem.filePath
                        && oldItem.fileName == newItem.fileName && oldItem.fileId == newItem.fileId
            }
        }
    }
}