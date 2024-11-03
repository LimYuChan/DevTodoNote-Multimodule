package com.note.note.presentation.photoviewer

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.note.feature_base.BaseFragment
import com.note.note.presentation.R
import com.note.note.presentation.databinding.FragmentPhotoViewerBinding

class PhotoViewerFragment : BaseFragment<FragmentPhotoViewerBinding>(R.layout.fragment_photo_viewer) {

    private val args by navArgs<PhotoViewerFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        loadImage()
    }

    private fun loadImage() {
        Glide.with(this)
            .load(args.imagePath)
            .into(binding.imageView)
    }

    private fun setupUI() {
        binding.actionBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}