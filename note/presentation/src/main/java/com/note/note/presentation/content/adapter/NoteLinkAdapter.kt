package com.note.note.presentation.content.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.note.note.domain.model.NoteLink
import com.note.note.presentation.R
import com.note.note.presentation.databinding.ItemNoteLinkBinding
import com.note.note.presentation.content.model.NoteLinkUi
import com.note.note.presentation.content.adapter.viewholder.NoteImageViewHolder
import com.note.note.presentation.content.adapter.viewholder.NoteLinkViewHolder

class NoteLinkAdapter(
    private val isDeleteAble: Boolean,
    private val action: (LinkItemAction) -> Unit
): ListAdapter<NoteLinkUi, NoteLinkViewHolder>(diffUtil) {

    sealed interface LinkItemAction {
        data class Delete(val data: NoteLinkUi): LinkItemAction
        data class OpenLink(val link: String): LinkItemAction
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteLinkViewHolder {
        val binding: ItemNoteLinkBinding =  DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_note_link,
            parent,
            false
        )

        return NoteLinkViewHolder(binding, isDeleteAble, action)
    }

    override fun onBindViewHolder(holder: NoteLinkViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<NoteLinkUi> () {
            override fun areItemsTheSame(oldItem: NoteLinkUi, newItem: NoteLinkUi): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: NoteLinkUi, newItem: NoteLinkUi): Boolean {
                return oldItem.isLoading == newItem.isLoading && oldItem.link.title == newItem.link.title
                        && oldItem.link.link == newItem.link.link && oldItem.link.description == newItem.link.description
                        && oldItem.link.image == newItem.link.image && oldItem.linkIndex == newItem.linkIndex
            }
        }
    }
}