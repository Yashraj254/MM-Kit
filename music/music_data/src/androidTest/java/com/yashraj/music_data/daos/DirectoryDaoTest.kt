package com.yashraj.music_data.daos

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.yashraj.database.MediaDatabase
import com.yashraj.database.daos.DirectoryDao
import com.yashraj.database.entities.DirectoryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DirectoryDaoTest {

    private lateinit var directoryDao: DirectoryDao
    private lateinit var database: MediaDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            MediaDatabase::class.java
        ).allowMainThreadQueries().build()
        directoryDao = database.directoryDao()
    }

    @After
    fun tearDown() {
        database.close()
    }


    // Test to check if the [DirectoryDao.upsert] method inserts a [DirectoryEntity] into the database.
    @Test
    fun directoryDao_inserts_directoryEntity() = runTest {
        val directoryEntity = sampleData[0]
        directoryDao.upsert(directoryEntity)

        val result = directoryDao.getAll().first()
        assert(result[0] == directoryEntity)
    }

    @Test
    fun directoryDao_insertsAll_directoryEntities() = runTest {
        directoryDao.upsertAll(sampleData)

        val result = directoryDao.getAll().first()
        assert(result == sampleData)
    }

    // Test to check if the [DirectoryDao.upsert] method updates a [DirectoryEntity] in the database.
    @Test
    fun directoryDao_updates_directoryEntity() = runTest {
        val directoryEntity = sampleData[0]
        directoryDao.upsert(directoryEntity)

        val updatedDirectoryEntity = sampleData[0].copy(name = "Something")
        directoryDao.upsert(updatedDirectoryEntity)

        val result = directoryDao.getAll().first()
        assert(result[0] == updatedDirectoryEntity)
    }

    // Test to check if the [DirectoryDao.delete] method deletes a [DirectoryEntity] from the database.
    @Test
    fun directoryDao_deletes_directoryEntity() = runTest {
        val directoryEntity = sampleData[0]
        directoryDao.upsert(directoryEntity)

        directoryDao.delete(listOf(directoryEntity.path))

        val result = directoryDao.getAll().first()
        assert(result.isEmpty())
    }



    val directory1 = DirectoryEntity("path1", "Directory 1", 1,1L,1L)
    val directory2 = DirectoryEntity("path2", "Directory 2", 2,2L,2L)
    val directory3 = DirectoryEntity("path3", "Directory 3", 3,3L,3L)
    val sampleData = listOf(directory1, directory2, directory3)

}


