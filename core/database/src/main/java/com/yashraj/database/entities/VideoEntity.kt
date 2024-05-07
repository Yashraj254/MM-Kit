package com.yashraj.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_tracks")
data class VideoEntity(
    @PrimaryKey(autoGenerate = true) var id: Long=0,
    @ColumnInfo(name = "date_added") var dateAdded: Int,
    @ColumnInfo(name = "duration") var duration: Int,
    @ColumnInfo(name = "folder_name") var folderName: String,
    @ColumnInfo(name = "folder_path") var folderPath: String,
    @ColumnInfo(name = "image_uri") var imageUri: String,
    @ColumnInfo(name = "media_store_id") var mediaStoreId: Long,
    @ColumnInfo(name = "path") var path: String,
    @ColumnInfo(name = "size") var size: Long,
    @ColumnInfo(name = "title") var title: String,
)
