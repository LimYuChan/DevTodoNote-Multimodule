package com.note.core.ui.dialog.confirm

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.note.core.ui.R
import com.note.core.ui.databinding.DialogConfirmBinding

class ConfirmDialog: DialogFragment() {

    var clickListener: ClickListener? = null

    private var _binding: DialogConfirmBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("뷰의 생명주기 밖에서 Binding에 접근하고 있습니다.")

    interface ClickListener {
        fun onConfirm()
        fun onCancel()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.RoundedDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogConfirmBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialogItem = getDialogItem() ?: run {
            dismiss()
            return
        }

        binding.apply {
            item = dialogItem

            btnCancel.isVisible = dialogItem.isCancelVisible

            if (dialogItem.isCancelVisible) {
                btnCancel.setText(getString(dialogItem.cancelButtonText))
            }

            btnConfirm.setText(getString(dialogItem.confirmButtonText))

            btnConfirm.setClickListener {
                clickListener?.onConfirm()
                dismiss()
            }

            btnCancel.setClickListener {
                clickListener?.onCancel()
                dismiss()
            }
        }
    }

    private fun getDialogItem(): ConfirmDialogItem? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable(ITEM_BUNDLE_KEY, ConfirmDialogItem::class.java)
            } else {
                arguments?.getParcelable(ITEM_BUNDLE_KEY)
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        clickListener = null
    }

    companion object {
        const val ITEM_BUNDLE_KEY = "item"
    }
}