package com.note.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.note.home.domain.model.Repository
import com.note.home.presentation.databinding.ItemRepositoryBinding

class UserRepositoryAdapter(
    private val onClick: (Repository) -> Unit = {}
): PagingDataAdapter<Repository, UserRepositoryViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRepositoryViewHolder {
        val binding: ItemRepositoryBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_repository, parent, false)
        return UserRepositoryViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: UserRepositoryViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    companion object {
        private val diffUtil = object: DiffUtil.ItemCallback<Repository> () {
            override fun areItemsTheSame(
                oldItem: Repository,
                newItem: Repository
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Repository,
                newItem: Repository
            ): Boolean {
                return oldItem.id == newItem.id && oldItem.name == newItem.name && oldItem.description == newItem.description
                        && oldItem.updatedAt == newItem.updatedAt
            }
        }
    }
}