package com.note.home.presentation

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.note.home.domain.model.Repository
import com.note.core.domain.extension.convertCurrentDateTime
import com.note.core.ui.extension.getLanguageColor
import com.note.core.ui.extension.setDrawableTint
import com.note.home.presentation.databinding.ItemRepositoryBinding

class UserRepositoryViewHolder(
    private val binding: ItemRepositoryBinding,
    private val onClick: (Repository) -> Unit
) : ViewHolder(binding.root){

    init {
        with(binding){
            itemRoot.setOnClickListener {
                repo?.let { item -> onClick.invoke(item) }
            }
        }
    }

    fun bind(data: Repository) {
        with(binding) {
            binding.repo = data
            tvRepoLanguage.setDrawableTint(tvRepoLanguage.context.getLanguageColor(data.language))
            tvRepoUpdatedAt.apply {
                text = data.updatedAt.convertCurrentDateTime()
                isVisible = text.isNotBlank()
            }
        }
    }
}