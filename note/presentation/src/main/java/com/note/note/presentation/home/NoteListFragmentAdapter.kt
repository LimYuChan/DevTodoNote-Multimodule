package com.note.note.presentation.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.note.note.domain.enums.NoteState
import com.note.note.presentation.home.NoteHomeFragment.Companion.TAB_LIST

class NoteListFragmentAdapter(
    fragment: Fragment,
    private val repositoryId: Int,
    private val repositoryName: String
): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return TAB_LIST.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            1 -> NoteListFragment.newInstance(repositoryId, repositoryName, NoteState.TODO)
            2 -> NoteListFragment.newInstance(repositoryId, repositoryName, NoteState.DONE)
            else -> NoteListFragment.newInstance(repositoryId, repositoryName)
        }
    }
}