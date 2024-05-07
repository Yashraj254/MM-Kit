package com.yashraj.video_data.daos

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.yashraj.database.MediaDatabase
import com.yashraj.database.daos.VideoDirectoryDao
import com.yashraj.database.entities.VideoDirectoryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VideoDirectoryDaoTest {

    private lateinit var videoDirectoryDao: VideoDirectoryDao
    private lateinit var database: MediaDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            MediaDatabase::class.java
        ).allowMainThreadQueries().build()
        videoDirectoryDao = database.videoDirectoryDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    // Test to check if the [VideoDirectoryDao.upsert] method inserts a [VideoDirectoryEntity] into the database.
    @Test
    fun videoDirectoryDao_inserts_videoDirectoryEntity() = runTest {
        val videoDirectoryEntity = sampleData[0]
        videoDirectoryDao.upsert(videoDirectoryEntity)

        val result = videoDirectoryDao.getAll().first().first()

        assert(result == videoDirectoryEntity)
    }

    // Test to check if the [VideoDirectoryDao.upsert] method updates a [VideoDirectoryEntity] in the database.
    @Test
    fun videoDirectoryDao_updates_videoDirectoryEntity() = runTest {
        val videoDirectoryEntity = sampleData[0]
        videoDirectoryDao.upsert(videoDirectoryEntity)

        val updatedVideoDirectoryEntity = sampleData[0].copy(name = "Something")
        videoDirectoryDao.upsert(updatedVideoDirectoryEntity)

        val result = videoDirectoryDao.getAll().first().first()

        assert(result == updatedVideoDirectoryEntity)
    }

    // Test to check if the [VideoDirectoryDao.getAll] method returns all the [VideoDirectoryEntity] from the database.
    @Test
    fun videoDirectoryDao_getAll() = runTest {
        videoDirectoryDao.upsertAll(sampleData)

        val result = videoDirectoryDao.getAll().first()

        assert(result == sampleData)
    }


    val directory1 = VideoDirectoryEntity("path1", "Directory 1", 1,1L,1L)
    val directory2 = VideoDirectoryEntity("path2", "Directory 2", 2,2L,2L)
    val directory3 = VideoDirectoryEntity("path3", "Directory 3", 3,3L,3L)
    val sampleData = listOf(directory1, directory2, directory3)}