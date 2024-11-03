package com.note.core.ui.extension

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph

fun NavController.safeNavigate(@IdRes resId: Int, args: Bundle? = null) {
    val destinationId = currentDestination?.getAction(resId)?.destinationId ?: 0
    currentDestination?.let { node ->
        val currentNode = when(node) {
            is NavGraph -> node
            else -> node.parent
        }
        if (destinationId != 0) {
            currentNode?.findNode(destinationId)?.let { navigate(resId, args) }
        }
    }
}

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run {
        navigate(direction)
    }
}