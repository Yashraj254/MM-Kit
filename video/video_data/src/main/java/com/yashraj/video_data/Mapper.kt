package com.yashraj.video_data

import androidx.media3.common.MediaItem
import com.yashraj.database.entities.VideoDirectoryEntity
import com.yashraj.database.entities.VideoEntity
import com.yashraj.utils.Utils
import com.yashraj.video_domain.models.Folder
import com.yashraj.video_domain.models.Video

fun MediaItem.toVideo() =
    Video(
        dateAdded = 0,
        duration = 0,
        folderName = "",
        folderPath = "",
        formattedDate = "",
        formattedDuration = "",
        formattedSize = "",
        size = 0,
        imageUri = mediaMetadata.artworkUri.toString(),
        mediaStoreId = 0L,
        path = mediaId,
        title = mediaMetadata.title.toString()
    )

fun VideoEntity.toVideo() = Video(
    dateAdded = dateAdded,
    duration = duration,
    folderName = folderName,
    folderPath = folderPath,
    formattedDate = Utils.formatDate(dateAdded.toLong()),
    formattedDuration = Utils.formatDurationMillis(duration.toLong()),
    formattedSize = Utils.formatFileSize(size.toLong()),
    imageUri = imageUri,
    mediaStoreId = mediaStoreId,
    path = path,
    size = size,
    title = title,
)

fun VideoDirectoryEntity.toFolder() = Folder(
    mediaCount = mediaCount,
    modified = modified,
    name = name,
    path = path,
    size = size
)