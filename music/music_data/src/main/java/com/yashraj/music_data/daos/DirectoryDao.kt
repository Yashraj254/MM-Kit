package com.yashraj.music_data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.yashraj.music_data.entities.DirectoryEntity
import com.yashraj.music_data.entities.MusicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DirectoryDao {

    @Upsert
    suspend fun upsert(directory: DirectoryEntity)

    @Upsert
    suspend fun upsertAll(directories: List<DirectoryEntity>)

    @Query("SELECT * FROM directories")
    fun getAll(): Flow<List<DirectoryEntity>>

    @Query("DELETE FROM directories WHERE path in (:paths)")
    suspend fun delete(paths: List<String>)
}