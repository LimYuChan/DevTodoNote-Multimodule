package com.note.core.ui.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import com.note.core.ui.R
import com.note.core.ui.constant.WidgetConstant
import com.note.core.ui.extension.convertActualSize
import androidx.core.widget.addTextChangedListener
import com.note.core.ui.extension.obtainInteger

class AdaptiveEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private var baseTextSize = WidgetConstant.BASE_TEXT_SIZE

    init {
        isFocusableInTouchMode = true
        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.AdaptiveEditText, 0, 0)
        baseTextSize = styledAttributes.obtainInteger(R.styleable.AdaptiveEditText_baseTextSizeAsPx)
        styledAttributes.recycle()

        textSize = resources.convertActualSize(baseTextSize)
    }

    private fun calculateSize() {
        textSize = resources.convertActualSize(baseTextSize)
    }

    fun setBaseTextSize(size: Int) {
        baseTextSize = size
        calculateSize()
        invalidate()
    }

    fun setBoldFontWhenTyping() {
        addTextChangedListener {
            if (it?.isEmpty() == true) {
                setTypeface(ResourcesCompat.getFont(context, R.font.pretendard_regular), Typeface.NORMAL)
            } else {
                setTypeface(ResourcesCompat.getFont(context, R.font.pretendard_bold), Typeface.NORMAL)
            }
        }
    }

    fun setMediumFontWhenTyping() {
        addTextChangedListener {
            if (it?.isEmpty() == true) {
                setTypeface(ResourcesCompat.getFont(context, R.font.pretendard_regular), Typeface.NORMAL)
            } else {
                setTypeface(ResourcesCompat.getFont(context, R.font.pretendard_bold), Typeface.NORMAL)
            }
        }
    }
}