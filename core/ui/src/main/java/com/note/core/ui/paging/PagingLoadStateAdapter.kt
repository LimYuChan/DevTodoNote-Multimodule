package com.note.core.ui.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import com.note.core.ui.R

class PagingLoadStateAdapter(
    private val adapter: PagingDataAdapter<*, *>
): LoadStateAdapter<PagingLoadStateViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PagingLoadStateViewHolder {
        return PagingLoadStateViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_load_state, parent, false))
    }

    override fun onBindViewHolder(holder: PagingLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
        holder.binding.btnRetry.setOnClickListener { adapter.retry() }
    }
}
