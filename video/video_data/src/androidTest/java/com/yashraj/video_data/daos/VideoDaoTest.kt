package com.yashraj.video_data.daos

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.yashraj.database.MediaDatabase
import com.yashraj.database.daos.VideoDao
import com.yashraj.database.entities.VideoEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VideoDaoTest {

    private lateinit var videoDao: VideoDao
    private lateinit var database: MediaDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            MediaDatabase::class.java
        ).allowMainThreadQueries().build()
        videoDao = database.videoDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    // Test to check if the [VideoDao.upsert] method inserts a [VideoEntity] into the database.
    @Test
    fun videoDao_inserts_videoEntity() = runTest {
        val videoEntity = sampleData[0]
        videoDao.upsertVideo(videoEntity)

        val result = videoDao.getVideoByPath(sampleData[0].path)

        assert(result == videoEntity)
    }

    // Test to check if the [VideoDao.upsert] method updates a [VideoEntity] in the database.
    @Test
    fun videoDao_updates_videoEntity() = runTest {
        val videoEntity = sampleData[0]
        videoDao.upsertVideo(videoEntity)

        val updatedVideoEntity = sampleData[0].copy(title = "Something")
        videoDao.upsertVideo(updatedVideoEntity)

        val result = videoDao.getVideoByPath(sampleData[0].path)

        assert(result == updatedVideoEntity)
    }

    // Test to check if the [VideoDao.getAllVideo] method returns all the [VideoEntity] from the database.
    @Test
    fun videoDao_getAllVideo() = runTest {
        videoDao.upsertAllVideo(sampleData)

        val result = videoDao.getAllVideo().first()

        assert(result == sampleData)
    }

    // Test to check if the [VideoDao.getVideoFromDirectory] method returns all the [VideoEntity] from the database for a given directory.
    @Test
    fun videoDao_getVideoFromDirectory() = runTest {
        videoDao.upsertAllVideo(sampleData)

        val result = videoDao.getVideoFromDirectory(sampleData[0].folderPath).first()

        assert(result[0] == sampleData[0])
    }

    // Test to check if the [VideoDao.delete] method deletes a [VideoEntity] from the database.
    @Test
    fun videoDao_delete() = runTest {
        videoDao.upsertAllVideo(sampleData)

        videoDao.delete(sampleData.map { it.path })

        val result = videoDao.getAllVideo().first()

        assert(result.isEmpty())
    }



    val video1 = VideoEntity(1, 1, 1, "folder 1", "folderPath 1", "image 1", 1L, "path 1", 1, "title 1")
    val video2 = VideoEntity(2, 2, 2, "folder 2", "folderPath 2", "image 2", 2L, "path 2", 2, "title 2")
    val video3 = VideoEntity(3, 3, 3, "folder 3", "folderPath 3", "image 3", 3L, "path 3", 3, "title 3")
    val sampleData = listOf(video1, video2, video3)

}