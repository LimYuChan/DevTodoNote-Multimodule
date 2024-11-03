package com.note.note.presentation.extensions

import android.widget.ImageView
import android.widget.TextView
import com.note.note.domain.enums.BranchState
import com.note.note.presentation.R

fun TextView.setBranchState(branchState: BranchState) {
    when(branchState){
        BranchState.COMMIT ->{
            text = context.getString(R.string.branch_commit)
            setTextColor(context.getColor(R.color.commit))
        }
        BranchState.MERGE ->{
            text = context.getString(R.string.branch_merge)
            setTextColor(context.getColor(R.color.merge))
        }
        else-> text = context.getString(com.note.core.ui.R.string.blank_text)
    }
}

fun ImageView.setBranchState(branchState: BranchState) {
    when(branchState) {
        BranchState.COMMIT -> setColorFilter(context.getColor(R.color.commit));
        BranchState.MERGE -> setColorFilter(context.getColor(R.color.merge));
        else->setColorFilter(context.getColor(com.note.core.ui.R.color.white));
    }
}