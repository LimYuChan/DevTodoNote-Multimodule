package com.note.core.ui.dialog.confirm

import android.os.Parcelable
import androidx.annotation.StringRes
import com.note.core.ui.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConfirmDialogItem (
    val title: String = "",
    val message: String = "",
    val isCancelVisible: Boolean = true,
    @StringRes val cancelButtonText: Int = R.string.cancel,
    @StringRes val confirmButtonText: Int = R.string.confirm
): Parcelable