package com.note.core.ui.paging

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.note.core.ui.databinding.ItemLoadStateBinding

class PagingLoadStateViewHolder(val binding: ItemLoadStateBinding): ViewHolder(binding.root) {
    fun bind(loadState: LoadState) {
        with(binding) {
            progressBar.isVisible = loadState is LoadState.Loading
            btnRetry.isVisible = loadState is LoadState.Error
            tvErrorMsg.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
            tvErrorMsg.text = (loadState as? LoadState.Error)?.error?.message
        }
    }
}