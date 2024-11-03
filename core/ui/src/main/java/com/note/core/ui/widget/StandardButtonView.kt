package com.note.core.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.note.core.common.Logg
import com.note.core.ui.R
import com.note.core.ui.databinding.ViewStandardButtonBinding
import com.note.core.ui.extension.obtainBoolean
import com.note.core.ui.extension.obtainString

class StandardButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewStandardButtonBinding = ViewStandardButtonBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.StandardButtonView, 0, 0)
        binding.lifecycleOwner = findViewTreeLifecycleOwner()
        val text = attributes.obtainString(R.styleable.StandardButtonView_text)
        val isLoading =
            attributes.obtainBoolean(R.styleable.StandardButtonView_loading)
        val isEnabled =
            attributes.obtainBoolean(R.styleable.StandardButtonView_enabled, defaultValue = true)
        val isOutlineStyle = attributes.obtainBoolean(R.styleable.StandardButtonView_outlineStyle)
        setText(text)
        setLoading(isLoading)
        setEnabled(isEnabled)
        setOutlineStyle(isOutlineStyle)

        attributes.recycle()
    }

    fun setText(text: String) {
        binding.buttonText = text
    }

    fun setLoading(isLoading: Boolean) {
        binding.isLoading = isLoading
        binding.enabled = !isLoading
    }

    override fun setEnabled(isEnable: Boolean) {
        binding.enabled = isEnable
    }

    fun setOutlineStyle(isOutline: Boolean) {
        binding.isOutline = isOutline
    }

    fun setClickListener(listener: OnClickListener) {
        binding.btnLogin.setOnClickListener(listener)
    }
}