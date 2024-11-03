package com.note.note.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewpager.widget.PagerAdapter
import com.note.note.domain.model.Note
import com.note.note.presentation.R
import com.note.note.presentation.databinding.ItemNoteListBinding
import com.note.note.presentation.home.viewholder.NoteViewHolder

class NoteAdapter(
    private val onNoteClick: (Long) -> Unit
): PagingDataAdapter<Note, NoteViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding: ItemNoteListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_note_list,
            parent,
            false
        )
        return NoteViewHolder(binding, onNoteClick)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.content.id == newItem.content.id && oldItem.content.branchState == newItem.content.branchState
                        && oldItem.content.content == newItem.content.content && oldItem.content.branchName == newItem.content.branchName
            }
        }
    }
}