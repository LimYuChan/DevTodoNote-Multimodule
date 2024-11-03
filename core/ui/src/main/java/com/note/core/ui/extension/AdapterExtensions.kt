package com.note.core.ui.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil


fun <T> ViewGroup.itemBinding(@LayoutRes layoutRes: Int): T {
    val inflater = LayoutInflater.from(context)
    return DataBindingUtil.inflate(inflater, layoutRes, this, false) as T
}