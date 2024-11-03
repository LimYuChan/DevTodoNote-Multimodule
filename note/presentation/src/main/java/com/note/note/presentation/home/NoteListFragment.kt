package com.note.note.presentation.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.note.core.ui.paging.PagingLoadStateAdapter
import com.note.feature_base.BaseFragment
import com.note.note.domain.enums.NoteState
import com.note.note.presentation.R
import com.note.note.presentation.databinding.FragmentNoteListBinding
import com.note.note.presentation.home.adapter.NoteAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListFragment : BaseFragment<FragmentNoteListBinding>(R.layout.fragment_note_list) {

    private val repositoryId: Int by lazy {
        arguments?.getInt(ARG_REPOSITORY_ID) ?: DEFAULT_REPOSITORY_ID
    }
    private val repositoryName: String by lazy {
        arguments?.getString(ARG_REPOSITORY_NAME) ?: DEFAULT_REPOSITORY_NAME
    }

    private val state: NoteState? by lazy {
        arguments?.getString(ARG_STATE)?.let { enumValueOf<NoteState>(it) }
    }

    private val viewModel: NoteListViewModel by viewModels()

    private val noteAdapter: NoteAdapter by lazy {
        NoteAdapter { contentId ->
            findNavController().navigate(
                R.id.noteViewerFragment,
                bundleOf(
                    "repositoryName" to repositoryName,
                    "noteId" to contentId
                )
            )
        }.apply {
            withLoadStateFooter(footer = PagingLoadStateAdapter(this))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        collectNotes()
    }

    private fun setupAdapter() {
        with(binding) {
            rvTodoNote.adapter = noteAdapter
            swipeRefreshLayout.setOnRefreshListener { noteAdapter.refresh() }
        }
    }

    private fun collectNotes() {
        collectLifecycleFlow(flow = viewModel.getNotes(repositoryId, state)) { notes ->
            noteAdapter.submitData(notes)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    companion object {
        private const val ARG_REPOSITORY_ID = "repositoryId"
        private const val ARG_REPOSITORY_NAME = "repositoryName"
        private const val ARG_STATE = "state"
        private const val DEFAULT_REPOSITORY_ID = 0
        private const val DEFAULT_REPOSITORY_NAME = "Unnamed"

        fun newInstance(repositoryId: Int, repositoryName: String, state: NoteState? = null): NoteListFragment {
            return NoteListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_REPOSITORY_ID, repositoryId)
                    putString(ARG_REPOSITORY_NAME, repositoryName)
                    putString(ARG_STATE, state?.name)
                }
            }
        }
    }
}