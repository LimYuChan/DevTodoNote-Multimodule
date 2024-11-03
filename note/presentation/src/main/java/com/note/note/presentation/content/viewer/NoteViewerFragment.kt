package com.note.note.presentation.content.viewer

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.color.MaterialColors.getColor
import com.note.core.ui.dialog.confirm.ConfirmDialog
import com.note.core.ui.dialog.confirm.ConfirmDialogItem
import com.note.core.ui.dialog.loading.LoadingDialog
import com.note.core.ui.extension.safeNavigate
import com.note.feature_base.BaseFragment
import com.note.note.domain.enums.BranchState
import com.note.note.presentation.R
import com.note.note.presentation.content.adapter.NoteImageAdapter
import com.note.note.presentation.content.adapter.NoteLinkAdapter
import com.note.note.presentation.content.write.NoteWriteAction
import com.note.note.presentation.databinding.FragmentNoteViewerBinding
import com.note.note.presentation.extensions.setBranchState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteViewerFragment: BaseFragment<FragmentNoteViewerBinding>(R.layout.fragment_note_viewer) {

    @Inject
    lateinit var factory: NoteViewerViewModel.Factory

    private val args: NoteViewerFragmentArgs by navArgs()

    private var loadingDialog: LoadingDialog? = null

    private val viewModel: NoteViewerViewModel by viewModels{
        NoteViewerViewModel.provideFactory(
            factory = factory,
            this,
            arguments,
            args.noteId,
            args.repositoryName
        )
    }

    private val imageAdapter: NoteImageAdapter by lazy {
        NoteImageAdapter(isDeleteAble = false) { action ->
            if(action is NoteImageAdapter.ImageItemAction.OpenViewer){
                val direction = NoteViewerFragmentDirections.actionNoteViewerFragmentToPhotoViewerFragment(action.filePath)
                findNavController().safeNavigate(direction)
            }
        }
    }

    private val linkAdapter: NoteLinkAdapter by lazy {
        NoteLinkAdapter(isDeleteAble = false) { action ->
            if(action is NoteLinkAdapter.LinkItemAction.OpenLink){
                Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(action.link)
                    context?.startActivity(this)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = viewModel
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog()
        }
        setupActionBar()
        setupUI()
        collectState()
        collectEvent()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNote()
    }

    private fun setupUI() {
        with(binding) {
            rvImage.adapter = imageAdapter
            rvLink.adapter = linkAdapter
        }
    }

    private fun setupActionBar() {
        with(binding.actionBar) {
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.action_edit -> viewModel.onAction(NoteViewerAction.EditNote)
                    R.id.action_delete -> showDeleteConfirmDialog()
                }
                true
            }
        }

    }

    private fun showDeleteConfirmDialog() {
        val dialogItem = ConfirmDialogItem(
            title = getString(R.string.delete_note_title),
            message = getString(R.string.delete_note_description)
        )
        val dialog = ConfirmDialog().apply {
            arguments = bundleOf(
                ConfirmDialog.ITEM_BUNDLE_KEY to dialogItem
            )
            clickListener = object: ConfirmDialog.ClickListener {
                override fun onConfirm() {
                    viewModel.onAction(NoteViewerAction.DeleteNote)
                }

                override fun onCancel() {
                }
            }
        }

        showDialogFragment(dialog)
    }


    private fun collectState() {
        collectLifecycleFlow(flow = viewModel.state) { state ->
            imageAdapter.submitList(state.images)
            linkAdapter.submitList(state.links)

            if(state.isLoading) {
                showDialogFragment(loadingDialog)
            }else{
                safeDismiss(loadingDialog)
            }

            val branchState = state.content.branchState
            with(binding) {
                tvBranchState.setBranchState(branchState)
                icBranchState.setBranchState(branchState)
            }
        }
    }


    private fun collectEvent() {
        collectLatestLifecycleFlow(flow = viewModel.event) { event ->
            when (event) {
                is NoteViewerEvent.Error -> {
                    showToast(event.error.asString(requireContext()))
                }
                is NoteViewerEvent.Edit -> {
                    val direction = NoteViewerFragmentDirections.actionNoteViewerFragmentToNoteWriteFragment(repositoryId = event.repositoryId, noteId = event.noteId)
                    findNavController().safeNavigate(direction)
                }
                is NoteViewerEvent.DeletedComplete -> {
                    showToast(getString(R.string.note_deleted_complete))
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loadingDialog = null
    }
}