package com.note.core.ui.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.note.core.ui.R
import com.note.core.ui.constant.WidgetConstant
import com.note.core.ui.extension.convertActualSize
import com.note.core.ui.extension.obtainInteger

class AdaptiveTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): AppCompatTextView(context, attrs, defStyleAttr) {

    private var baseTextSize = WidgetConstant.BASE_TEXT_SIZE
    private var basePaddingTop = WidgetConstant.BASE_PADDING
    private var basePaddingBottom = WidgetConstant.BASE_PADDING


    init {
        val styleTypedArray = context.obtainStyledAttributes(attrs, R.styleable.AdaptiveTextView, 0, 0)

        baseTextSize = styleTypedArray.obtainInteger(R.styleable.AdaptiveTextView_baseTextSizeAsPx)
        basePaddingTop = styleTypedArray.obtainInteger(R.styleable.AdaptiveTextView_basePaddingTopAsPx)
        basePaddingBottom = styleTypedArray.obtainInteger(R.styleable.AdaptiveTextView_basePaddingBottomAsPx)

        calculateSize()
        styleTypedArray.recycle()
    }

    private fun calculateSize(){
        textSize = resources.convertActualSize(baseTextSize)

        val heightRatio = resources.displayMetrics.heightPixels / WidgetConstant.BASE_HEIGHT
        val calculatePaddingTop = if(basePaddingTop != WidgetConstant.DEFAULT_VALUE){
            (heightRatio * basePaddingTop).toInt()
        }else{
            paddingTop
        }
        val calculatePaddingBottom = if(basePaddingBottom != WidgetConstant.DEFAULT_VALUE){
            (heightRatio * basePaddingBottom).toInt()
        }else{
            paddingBottom
        }
        setPadding(paddingLeft, calculatePaddingTop, paddingRight, calculatePaddingBottom)
    }

    fun setBaseTextSize(size: Int) {
        baseTextSize = size
        calculateSize()
        invalidate()
    }
}