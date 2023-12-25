package com.yashraj.video_data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.yashraj.video_data.entities.VideoDirectoryEntity
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