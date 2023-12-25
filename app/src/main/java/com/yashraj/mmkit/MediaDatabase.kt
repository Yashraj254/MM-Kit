package com.yashraj.mmkit

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yashraj.music_data.daos.DirectoryDao
import com.yashraj.music_data.daos.MusicDao
import com.yashraj.music_data.daos.PlaylistDao
import com.yashraj.music_data.entities.DirectoryEntity
import com.yashraj.music_data.entities.MusicEntity
import com.yashraj.music_data.entities.PlaylistEntity
import com.yashraj.video_data.daos.VideoDao
import com.yashraj.video_data.daos.VideoDirectoryDao
import com.yashraj.video_data.entities.VideoDirectoryEntity
import com.yashraj.video_data.entities.VideoEntity

@Database(
    entities = [
        MusicEntity::class,
        PlaylistEntity::class,
        DirectoryEntity::class,
        VideoEntity::class,
        VideoDirectoryEntity::class
    ],
    version = 1,
)
abstract class MediaDatabase : RoomDatabase() {

    abstract fun musicDao(): MusicDao

    abstract fun playlistDao(): PlaylistDao

    abstract fun videoDao(): VideoDao

    abstract fun directoryDao(): DirectoryDao

    abstract fun videoDirectoryDao(): VideoDirectoryDao

    companion object {
        const val DATABASE_NAME = "multimedia_db"
    }
}
