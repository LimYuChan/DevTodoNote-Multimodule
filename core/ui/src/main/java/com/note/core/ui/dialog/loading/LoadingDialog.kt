package com.note.core.ui.dialog.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.note.core.ui.databinding.DialogLoadingBinding

class LoadingDialog: DialogFragment() {

    private var _binding: DialogLoadingBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("뷰의 생명주기 밖에서 Binding에 접근하고 있습니다")

    override fun onStart() {
        super.onStart()
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}