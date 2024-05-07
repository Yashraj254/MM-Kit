package com.yashraj.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yashraj.database.daos.DirectoryDao
import com.yashraj.database.daos.MusicDao
import com.yashraj.database.daos.PlaylistDao
import com.yashraj.database.daos.VideoDao
import com.yashraj.database.daos.VideoDirectoryDao
import com.yashraj.database.entities.DirectoryEntity
import com.yashraj.database.entities.MusicEntity
import com.yashraj.database.entities.PlaylistEntity
import com.yashraj.database.entities.VideoDirectoryEntity
import com.yashraj.database.entities.VideoEntity

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
