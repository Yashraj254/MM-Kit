package com.yashraj.music_data.daos

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.yashraj.database.MediaDatabase
import com.yashraj.database.daos.PlaylistDao
import com.yashraj.database.entities.PlaylistEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PlaylistDaoTest {

    private lateinit var playlistDao: PlaylistDao
    private lateinit var database: MediaDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            MediaDatabase::class.java
        ).build()
        playlistDao = database.playlistDao()
    }

    // Test to check if the [PlaylistDao.upsert] method inserts a [PlaylistEntity] into the database.
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun playlistDao_inserts_playlistEntity() = runTest {
        val playlistEntity = sampleData[0]
        playlistDao.upsert(playlistEntity)

        val result = playlistDao.getAllPlaylists().first()

        assert(result[0] == playlistEntity)

    }

    val playlist1 = PlaylistEntity(1, "Playlist 1")
    val playlist2 = PlaylistEntity(2, "Playlist 2")
    val playlist3 = PlaylistEntity(3, "Playlist 3")
    val sampleData = listOf(playlist1, playlist2, playlist3)

}


