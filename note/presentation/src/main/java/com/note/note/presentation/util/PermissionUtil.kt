package com.note.note.presentation.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

fun ComponentActivity.shouldShowStoragePermissionRationale(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES)
    } else {
        shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}

// 저장소 권한이 있는지 확인
private fun Context.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.hasStoragePermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        hasPermission(Manifest.permission.READ_MEDIA_IMAGES)
    } else {
        hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}