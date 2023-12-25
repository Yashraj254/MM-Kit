package com.yashraj.video_domain.models

data class Video(
    var dateAdded: Int,
    val duration: Int,
    var folderName: String,
    var folderPath: String,
    var formattedDate: String,
    var formattedDuration: String,
    var formattedSize: String,
    val imageUri: String,
    var mediaStoreId: Long,
    val path: String,
    var size: Long,
    val title: String,
)
