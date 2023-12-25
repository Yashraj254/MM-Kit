package com.yashraj.video_data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.yashraj.video_data.entities.VideoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {

    @Upsert
    suspend fun upsertVideo(track: VideoEntity)

    @Upsert
    suspend fun upsertAllVideo(tracks: List<VideoEntity>)

    @Query("SELECT * FROM video_tracks")
    fun getAllVideo(): Flow<List<VideoEntity>>

    @Query("SELECT * FROM video_tracks WHERE path = :path")
    suspend fun getVideoByPath(path: String): VideoEntity?

    @Query("SELECT * FROM video_tracks WHERE folder_path = :directoryPath")
    fun getVideoFromDirectory(directoryPath: String): Flow<List<VideoEntity>>

    @Query("DELETE FROM video_tracks WHERE path in (:paths)")
    suspend fun delete(paths: List<String>)

}