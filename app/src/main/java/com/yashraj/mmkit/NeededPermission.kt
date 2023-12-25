package com.yashraj.mmkit

import android.os.Build
import androidx.annotation.RequiresApi

enum class NeededPermission(
    val permission: String,
    val title: String,
    val description: String,
    val permanentlyDeniedDescription: String,
) {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    READ_MEDIA_AUDIO(
        permission = android.Manifest.permission.READ_MEDIA_AUDIO,
        title = "Read Audio Permission",
        description = "This permission is needed to read your audio. Please grant the permission.",
        permanentlyDeniedDescription = "This permission is needed to read your audio. Please grant the permission in app settings.",
    ),
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    READ_MEDIA_VIDEO(
        permission = android.Manifest.permission.READ_MEDIA_VIDEO,
        title = "Read Videos Permission",
        description = "This permission is needed to read your videos. Please grant the permission.",
        permanentlyDeniedDescription = "This permission is needed to read your videos. Please grant the permission in app settings.",
    );

    fun permissionTextProvider(isPermanentDenied: Boolean): String {
        return if (isPermanentDenied) this.permanentlyDeniedDescription else this.description
    }
}

fun getNeededPermission(permission: String): NeededPermission {
    return NeededPermission.values().find { it.permission == permission }
        ?: throw IllegalArgumentException("Permission $permission is not supported")
}