package com.note.note.presentation.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.note.core.ui.extension.safeNavigate
import com.note.feature_base.BaseFragment
import com.note.note.presentation.R
import com.note.note.presentation.databinding.FragmentNoteHomeBinding
import com.note.note.presentation.util.ViewpagerTransform

class NoteHomeFragment : BaseFragment<FragmentNoteHomeBinding>(R.layout.fragment_note_home) {

    private val args: NoteHomeFragmentArgs by navArgs()
    private lateinit var fragmentAdapter: NoteListFragmentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupViewPager()
    }

    private fun setupUI() {
        with(binding) {
            repositoryName = args.repositoryName
            btnCreateNote.setOnClickListener {
                findNavController().safeNavigate(
                    NoteHomeFragmentDirections.actionNoteHomeFragmentToNoteWriteFragment(
                        args.repositoryId
                    )
                )
            }
        }
    }

    private fun setupViewPager() {
        fragmentAdapter = NoteListFragmentAdapter(
            fragment = this@NoteHomeFragment,
            repositoryName = args.repositoryName,
            repositoryId = args.repositoryId
        )

        with(binding) {
            viewpagerNoteState.adapter = fragmentAdapter
            viewpagerNoteState.isUserInputEnabled = false

            TabLayoutMediator(tabNoteState, viewpagerNoteState) { tab, position ->
                tab.text = TAB_LIST[position]
            }.attach()
            viewpagerNoteState.setPageTransformer(ViewpagerTransform())
        }
    }

    companion object {
        val TAB_LIST = listOf("All", "Todo", "Done")
    }
}