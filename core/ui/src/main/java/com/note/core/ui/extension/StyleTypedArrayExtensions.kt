package com.note.core.ui.extension

import android.content.res.TypedArray
import com.note.core.ui.constant.WidgetConstant


fun TypedArray.obtainInteger(
    styleId: Int,
    defaultValue: Int = WidgetConstant.DEFAULT_VALUE
): Int {
    return try {
        getInteger(styleId, defaultValue)
    }catch (exception: Exception) {
        defaultValue
    }
}

fun TypedArray.obtainColor(
    styleId: Int,
    defaultValue: Int = WidgetConstant.DEFAULT_VALUE
): Int {
    return try {
        getColor(styleId, defaultValue)
    }catch (exception: Exception) {
        defaultValue
    }
}

fun TypedArray.obtainResource(
    styleId: Int,
    defaultId: Int = WidgetConstant.DEFAULT_VALUE
): Int{
    return try{
        getResourceId(styleId, defaultId)
    }catch (exception: Exception){
        defaultId
    }
}

fun TypedArray.obtainBoolean(
    styleId: Int,
    defaultValue: Boolean = false
): Boolean{
    return try{
        getBoolean(styleId, defaultValue)
    }catch (exception: Exception){
        defaultValue
    }
}

fun TypedArray.obtainString(
    styleId: Int,
    defaultValue: String = ""
): String{
    return try{
        getString(styleId) ?: ""
    }catch (exception: Exception){
        defaultValue
    }
}