package com.yashraj.music_domain.models

data class Music(
    val album: String,
    val albumId: Long,
    val artist: String,
    val artistId: Long,
    var dateAdded: Int,
    val duration: Int,
    var favorite: Int,
    var folderName: String,
    var folderPath: String,
    var formattedDate: String,
    var formattedDuration: String,
    var formattedSize: String,
    val imageUri: String,
    var isSelected: Boolean = false,
    var mediaStoreId: Long,
    val path: String,
    var playlistId: Int,
    var size: Int,
    val title: String,
)
