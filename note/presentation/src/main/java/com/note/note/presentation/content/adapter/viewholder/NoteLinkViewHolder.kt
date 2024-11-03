package com.note.note.presentation.content.adapter.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.note.note.presentation.databinding.ItemNoteLinkBinding
import com.note.note.presentation.content.adapter.NoteLinkAdapter
import com.note.note.presentation.content.model.NoteLinkUi

class NoteLinkViewHolder(
    private val binding: ItemNoteLinkBinding,
    private val isDeleteAble: Boolean,
    private val action: (NoteLinkAdapter.LinkItemAction) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    init {
        with(binding) {
            btnRemove.setOnClickListener {
                if(isDeleteAble) {
                    linkUi?.let { item -> action(NoteLinkAdapter.LinkItemAction.Delete(item)) }
                }
            }

            tvLink.setOnClickListener {
                linkUi?.let { item -> action(NoteLinkAdapter.LinkItemAction.OpenLink(item.link.link)) }
            }
        }
    }

    fun bind(data: NoteLinkUi) {
        with(binding) {
            linkUi = data

            btnRemove.isVisible = isDeleteAble

            Glide.with(image)
                .load(data.link.image)
                .transform(RoundedCorners(12))
                .into(image)
        }
    }
}