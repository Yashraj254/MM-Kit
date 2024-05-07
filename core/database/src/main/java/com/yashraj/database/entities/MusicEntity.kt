package com.yashraj.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "music_tracks")
data class MusicEntity(
    @PrimaryKey(autoGenerate = true) var id: Long=0,
    @ColumnInfo(name = "album") var album: String,
    @ColumnInfo(name = "album_id") var albumId: Long,
    @ColumnInfo(name = "artist") var artist: String,
    @ColumnInfo(name = "artist_id") var artistId: Long,
    @ColumnInfo(name = "date_added") var dateAdded: Int,
    @ColumnInfo(name = "duration") var duration: Int,
    @ColumnInfo(name = "favorite") var favorite: Int,
    @ColumnInfo(name = "folder_name") var folderName: String,
    @ColumnInfo(name = "folder_path") var folderPath: String,
    @ColumnInfo(name = "image_uri") var imageUri: String,
    @ColumnInfo(name = "media_store_id") var mediaStoreId: Long,
    @ColumnInfo(name = "path") var path: String,
    @ColumnInfo(name = "playlist_id") var playlistId: Int,
    @ColumnInfo(name = "size") var size: Int,
    @ColumnInfo(name = "title") var title: String,
)