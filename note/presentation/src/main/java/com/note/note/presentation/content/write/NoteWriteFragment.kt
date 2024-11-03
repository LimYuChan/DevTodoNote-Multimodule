package com.note.note.presentation.content.write

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerLauncher
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.note.core.ui.dialog.input.InputTextDialog
import com.note.core.ui.dialog.input.InputTextDialogItem
import com.note.core.ui.dialog.loading.LoadingDialog
import com.note.core.ui.extension.safeNavigate
import com.note.feature_base.BaseFragment
import com.note.note.presentation.R
import com.note.note.presentation.content.adapter.NoteImageAdapter
import com.note.note.presentation.content.adapter.NoteLinkAdapter
import com.note.note.presentation.databinding.FragmentNoteWriteBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteWriteFragment : BaseFragment<FragmentNoteWriteBinding>(R.layout.fragment_note_write) {

    @Inject
    lateinit var factory: NoteWriteViewModel.Factory

    private val args: NoteWriteFragmentArgs by navArgs()

    private var loadingDialog: LoadingDialog? = null

    private val viewModel: NoteWriteViewModel by viewModels {
        NoteWriteViewModel.provideFactory(
            factory = factory,
            this,
            arguments,
            args.noteId,
            args.repositoryId
        )
    }

    private val imagePickerLauncher: ImagePickerLauncher = registerImagePicker {
        viewModel.onAction(NoteWriteAction.AddImages(it))
    }

    private val imageAdapter: NoteImageAdapter by lazy {
        NoteImageAdapter(isDeleteAble = true) { action ->
            when (action) {
                is NoteImageAdapter.ImageItemAction.Delete -> {
                    viewModel.onAction(NoteWriteAction.RemoveImage(action.data))
                }

                is NoteImageAdapter.ImageItemAction.OpenViewer -> {
                    val direction =
                        NoteWriteFragmentDirections.actionNoteWriteFragmentToPhotoViewerFragment(
                            action.filePath
                        )
                    findNavController().safeNavigate(direction)
                }
            }
        }
    }

    private val linkAdapter: NoteLinkAdapter by lazy {
        NoteLinkAdapter(isDeleteAble = true) { action ->
            when (action) {
                is NoteLinkAdapter.LinkItemAction.Delete -> {
                    viewModel.onAction(NoteWriteAction.RemoveLink(action.data))
                }

                is NoteLinkAdapter.LinkItemAction.OpenLink -> {
                    Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(action.link)
                        context?.startActivity(this)
                    }
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = viewModel
        if(loadingDialog == null) {
            loadingDialog = LoadingDialog()
        }

        setupActionBar()
        setupUI()
        collectState()
        collectEvent()
    }

    private fun setupUI() {
        with(binding) {
            rvImage.adapter = imageAdapter
            rvLink.adapter = linkAdapter

            btnAddImage.setOnClickListener {
                launchImagePicker()
            }

            btnAddLink.setOnClickListener {
                showInputTextDialog()
            }
        }
    }

    private fun launchImagePicker() {
        val config = ImagePickerConfig(
            mode = ImagePickerMode.MULTIPLE,
            theme = R.style.NotePicker,
            isShowCamera = false,
            isIncludeVideo = false,
            isFolderMode = false
        )
        imagePickerLauncher.launch(config)
    }

    private fun setupActionBar() {
        with(binding.actionBar) {
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            setOnMenuItemClickListener {
                if (it.itemId == R.id.action_save) {
                    viewModel.onAction(NoteWriteAction.Submit)
                }
                true
            }
        }
    }

    private fun showInputTextDialog() {
        val dialogItem = InputTextDialogItem(
            title = getString(R.string.input_link_title),
            hint = getString(R.string.input_link)
        )
        val dialog = InputTextDialog().apply {
            arguments = bundleOf(
                InputTextDialog.ITEM_BUNDLE_KEY to dialogItem
            )
            clickListener = object : InputTextDialog.ClickListener {
                override fun onConfirm(text: String) {
                    viewModel.onAction(NoteWriteAction.AddLink(text))
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
        }
    }


    private fun collectEvent() {
        collectLatestLifecycleFlow(flow = viewModel.event) {
            when (it) {
                is NoteWriteEvent.Error -> {
                    showToast(it.error.asString(requireContext()))
                }

                is NoteWriteEvent.SavedComplete -> {
                    showToast(getString(R.string.note_saved))
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