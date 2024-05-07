package com.yashraj.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.yashraj.database.entities.VideoDirectoryEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface VideoDirectoryDao {

    @Upsert
    suspend fun upsert(directory: VideoDirectoryEntity)

    @Upsert
    suspend fun upsertAll(directories: List<VideoDirectoryEntity>)

    @Query("SELECT * FROM video_directories")
    fun getAll(): Flow<List<VideoDirectoryEntity>>

    @Query("DELETE FROM video_directories WHERE path in (:paths)")
    suspend fun delete(paths: List<String>)
}