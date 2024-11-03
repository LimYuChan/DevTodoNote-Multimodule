package com.note.note.presentation.home.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.note.core.common.Logg
import com.note.note.domain.model.Note
import com.note.note.presentation.databinding.ItemNoteListBinding
import com.note.note.presentation.extensions.setBranchState

class NoteViewHolder(
    private val binding: ItemNoteListBinding,
    private val onNoteClick: (Long) -> Unit
): ViewHolder(binding.root){

    init {
        with(binding) {
            itemRoot.setOnClickListener {
                note?.let { onNoteClick.invoke(it.content.id) }
            }
        }
    }

    fun bind(data: Note) {
        with(binding) {
            note = data

            val branchState = data.content.branchState
            itemBranchState.setBranchState(branchState)
            itemBranchStateIcon.setBranchState(branchState)
        }
    }
}