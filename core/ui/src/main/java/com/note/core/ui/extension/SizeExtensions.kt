package com.note.core.ui.extension

import android.content.res.Resources
import android.util.TypedValue
import com.note.core.ui.constant.WidgetConstant.BASE_WIDTH
import com.note.core.ui.constant.WidgetConstant.MAX_WIDTH

fun Resources.convertActualSize(baseTextSize: Int): Float {
    val ratio = displayMetrics.widthPixels.coerceAtMost(MAX_WIDTH.toInt()) / displayMetrics.density / BASE_WIDTH
    val preprocessedSize = baseTextSize * ratio
    val notScalingRatio = displayMetrics.density / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, displayMetrics)
    return preprocessedSize * notScalingRatio
}

fun Int.dpToPx(): Int{
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

fun Float.dpToPx(): Float{
    return this * Resources.getSystem().displayMetrics.density
}