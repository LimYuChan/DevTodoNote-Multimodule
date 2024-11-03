package com.note.core.ui.dialog.input

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.note.core.ui.R
import com.note.core.ui.databinding.DialogInputTextBinding

class InputTextDialog : DialogFragment() {

    var clickListener: ClickListener? = null

    private var _binding: DialogInputTextBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("뷰의 생명주기 밖에서 Binding에 접근하고 있습니다.")

    interface ClickListener {
        fun onConfirm(text: String)
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
    ): View? {
        _binding = DialogInputTextBinding.inflate(inflater, container, false)
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
                clickListener?.onConfirm(etInput.text.toString())
                dismiss()
            }

            btnCancel.setClickListener {
                clickListener?.onCancel()
                dismiss()
            }
        }
    }

    private fun getDialogItem(): InputTextDialogItem? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable(ITEM_BUNDLE_KEY, InputTextDialogItem::class.java)
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
