package com.yashraj.music_data.daos

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.yashraj.database.MediaDatabase
import com.yashraj.database.daos.MusicDao
import com.yashraj.database.entities.MusicEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MusicDaoTest {

    private lateinit var musicDao: MusicDao
    private lateinit var database: MediaDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            MediaDatabase::class.java
        ).build()
        musicDao = database.musicDao()
    }

    // Test to check if the [MusicDao.upsert] method inserts a [MusicEntity] into the database.
    @Test
    fun musicDao_inserts_musicEntity() = runTest {
        val musicEntity = sampleData[0]
        musicDao.upsertMusic(musicEntity)

        val result = musicDao.getMusicByPath(sampleData[0].path)

        assert(result == musicEntity)
    }

    // Test to check if the [MusicDao.upsert] method updates a [MusicEntity] in the database.
    @Test
    fun musicDao_updates_musicEntity() = runTest {
        val musicEntity = sampleData[0]
        musicDao.upsertMusic(musicEntity)

        val updatedMusicEntity = sampleData[0].copy(title = "Something")
        musicDao.upsertMusic(updatedMusicEntity)

        val result = musicDao.getMusicByPath(sampleData[0].path)

        assert(result == updatedMusicEntity)
    }

    // Test to check if the [MusicDao.getAllMusic] method returns all the [MusicEntity] from the database.
    @Test
    fun musicDao_getAllMusic() = runTest {
        musicDao.upsertAllMusic(sampleData)

        val result = musicDao.getAllMusic().first()

        assert(result == sampleData)
    }

    // Test to check if the [MusicDao.getMusicFavorites] method returns all the favorite [MusicEntity] from the database.
    @Test
    fun musicDao_getMusicFavorites() = runTest {
        sampleData.forEach {
            it.favorite = 1
        }
        musicDao.upsertAllMusic(sampleData)

        val result = musicDao.getMusicFavorites().first()

        assert(result[0] == sampleData[0])
    }

    // Test to check if the [MusicDao.getPlaylistMusic] method returns all the [MusicEntity] from the database for a given playlist.
    @Test
    fun musicDao_getPlaylistMusic() = runTest {
        musicDao.upsertAllMusic(sampleData)

        val result = musicDao.getPlaylistMusic(1).first()

        assert(result[0] == sampleData[0])
    }

    // Test to check if the [MusicDao.getMusicByPath] method returns a [MusicEntity] from the database for a given path.
    @Test
    fun musicDao_getMusicByPath() = runTest {
        musicDao.upsertAllMusic(sampleData)

        val result = musicDao.getMusicByPath(sampleData[0].path)

        assert(result == sampleData[0])
    }

    // Test to check if the [MusicDao.getMusicFromDirectory] method returns all the [MusicEntity] from the database for a given directory.
    @Test
    fun musicDao_getMusicFromDirectory() = runTest {
        musicDao.upsertMusic(sampleData[0])

        val result = musicDao.getMusicFromDirectory(sampleData[0].folderPath).first()

        assert(result[0] == sampleData[0])
    }

    // Test to check if the [MusicDao.delete] method deletes a [MusicEntity] from the database.
    @Test
    fun musicDao_delete() = runTest {
        musicDao.upsertAllMusic(sampleData)

        musicDao.delete(sampleData.map { it.path })

        val result = musicDao.getAllMusic().first()

        assert(result.isEmpty())
    }

    // Test to check if the [MusicDao.addToFavorites] method updates the favorite field of a [MusicEntity] in the database.
    @Test
    fun musicDao_addToFavorites() = runTest {
        musicDao.upsertAllMusic(sampleData)

        musicDao.addToFavorites(sampleData[0].path)

        val result = musicDao.getMusicByPath(sampleData[0].path)

        assert(result?.favorite == 1)
    }

    // Test to check if the [MusicDao.removeFromFavorites] method updates the favorite field of a [MusicEntity] in the database.
    @Test
    fun musicDao_removeFromFavorites() = runTest {
        musicDao.upsertAllMusic(sampleData)

        musicDao.removeFromFavorites(sampleData[0].path)

        val result = musicDao.getMusicByPath(sampleData[0].path)

        assert(result?.favorite == 0)
    }

    // Test to check if the [MusicDao.removeFromPlaylist] method updates the playlistId field of a [MusicEntity] in the database.
    @Test
    fun musicDao_removeFromPlaylist() = runTest {
        musicDao.upsertAllMusic(sampleData)

        musicDao.removeFromPlaylist(sampleData[0].path)

        val result = musicDao.getMusicByPath(sampleData[0].path)

        assert(result?.playlistId == 0)
    }




    val music1 = MusicEntity(
        1,
        "album 1",
        1L,
        "artist 1",
        1L,
        1,
        1,
        0,
        "folder 1",
        "folderPath 1",
        "image 1",
        1L,
        "path 1",
        1,
        1,
        "title 1"
    )
    val music2 = MusicEntity(
        2,
        "album 2",
        2L,
        "artist 2",
        2L,
        2,
        2,
        0,
        "folder 2",
        "folderPath 2",
        "image 2",
        2L,
        "path 2",
        2,
        2,
        "title 2"
    )
    val music3 = MusicEntity(
        3,
        "album 3",
        3L,
        "artist 3",
        3L,
        3,
        3,
        0,
        "folder 3",
        "folderPath 3",
        "image 3",
        3L,
        "path 3",
        3,
        3,
        "title 3"
    )
    val sampleData = listOf(music1, music2, music3)
}