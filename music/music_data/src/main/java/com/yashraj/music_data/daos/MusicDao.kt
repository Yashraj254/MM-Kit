package com.yashraj.music_data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.yashraj.music_data.entities.MusicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDao {

    @Upsert
    suspend fun upsertMusic(track: MusicEntity)

    @Upsert
    suspend fun upsertAllMusic(tracks: List<MusicEntity>)

    @Query("SELECT * FROM music_tracks")
    fun getAllMusic(): Flow<List<MusicEntity>>

    @Query("SELECT * FROM music_tracks WHERE favorite = 1")
    fun getMusicFavorites(): Flow<List<MusicEntity>>

    @Query("SELECT * FROM music_tracks WHERE playlist_id = :playlistId")
    fun getPlaylistMusic(playlistId: Int): Flow<List<MusicEntity>>

    @Query("SELECT * FROM music_tracks WHERE path = :path")
    suspend fun getMusicByPath(path: String): MusicEntity?

    @Query("SELECT * FROM music_tracks WHERE folder_path = :directoryPath")
    fun getMusicFromDirectory(directoryPath: String): Flow<List<MusicEntity>>

    @Query("DELETE FROM music_tracks WHERE path in (:paths)")
    suspend fun delete(paths: List<String>)

    @Query("UPDATE music_tracks SET favorite = 1 WHERE path = :path")
    suspend fun addToFavorites(path: String)

    @Query("UPDATE music_tracks SET favorite = 0 WHERE path = :path")
    suspend fun removeFromFavorites(path: String)

}