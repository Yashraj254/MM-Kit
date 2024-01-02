package com.yashraj.video_data

import android.content.ContentResolver
import android.content.ContentUris
import android.database.ContentObserver
import android.net.Uri
import android.provider.MediaStore
import com.yashraj.utils.MUSIC_COLLECTION_URI
import com.yashraj.utils.VIDEO_COLLECTION_URI
import com.yashraj.video_data.daos.VideoDirectoryDao
import com.yashraj.video_data.daos.VideoDao
import com.yashraj.video_data.entities.VideoDirectoryEntity
import com.yashraj.video_data.entities.VideoEntity
import com.yashraj.video_domain.models.Video
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class VideoDataSource @Inject constructor(
    private val contentResolver: ContentResolver,
    private val videoDao: VideoDao,
    private val directoryDao: VideoDirectoryDao,
) : VideoSynchronizer {

    private var videoSyncingJob: Job? = null

    private val projection = arrayOf(
        MediaStore.Video.Media.DATE_ADDED,
        MediaStore.Video.Media.DATA,
        MediaStore.Video.Media.DURATION,
        MediaStore.Video.Media._ID,
        MediaStore.Video.Media.SIZE,
        MediaStore.Video.Media.DISPLAY_NAME,
    )

    override fun startSync() {
        if (videoSyncingJob != null) return
        videoSyncingJob = getVideoFlow().onEach { video ->
            CoroutineScope(Dispatchers.IO).launch { updateVideoDirectories(video) }
            CoroutineScope(Dispatchers.IO).launch { updateVideo(video) }
        }.launchIn(CoroutineScope(SupervisorJob() + Dispatchers.IO))
    }

    override fun stopSync() {
        videoSyncingJob?.cancel()
    }

    private suspend fun updateVideoDirectories(video: List<Video>) = withContext(
        Dispatchers.Default
    ) {
        val directories = video.groupBy { File(it.path).parentFile!! }.map { (file, videos) ->
            VideoDirectoryEntity(
                path = file.path,
                name = file.name,
                mediaCount = video.size,
                size = videos.sumOf {
                    it.size.toLong()
                },
                modified = file.lastModified()
            )
        }
        directoryDao.upsertAll(directories)

        val currentDirectoryPaths = directories.map { it.path }

        val unwantedDirectories = directoryDao.getAll().first()
            .filterNot { it.path in currentDirectoryPaths }

        val unwantedDirectoriesPaths = unwantedDirectories.map { it.path }

        directoryDao.delete(unwantedDirectoriesPaths)
    }

    private suspend fun updateVideo(video: List<Video>) = withContext(Dispatchers.IO) {
        val videoEntities = video.map {
            val file = File(it.path)
            val videoEntity = videoDao.getVideoByPath(it.path)
            videoEntity?.copy(
                dateAdded = it.dateAdded,
                duration = it.duration,
                folderName = it.folderName,
                folderPath = file.parent!!,
                imageUri = it.imageUri,
                mediaStoreId = it.mediaStoreId,
                path = it.path,
                size = it.size,
                title = it.title
            ) ?: VideoEntity(
                    dateAdded = it.dateAdded,
                    duration = it.duration,
                    folderName = it.folderName,
                    folderPath = file.parent!!,
                    imageUri = it.imageUri,
                    mediaStoreId = it.mediaStoreId,
                    path = it.path,
                    size = it.size,
                    title = it.title,
                )
        }

        videoDao.upsertAllVideo(videoEntities)

        val currentMediaPaths = videoEntities.map {
            it.path
        }

        val unwantedMedia = videoDao.getAllVideo().first()
            .filterNot { it.path in currentMediaPaths }

        val unwantedMediaPaths = unwantedMedia.map { it.path }

        videoDao.delete(unwantedMediaPaths)

    }

    private fun getVideoFlow(): Flow<List<Video>> = callbackFlow {
        val observer = object : ContentObserver(null) {
            override fun onChange(selfChange: Boolean) {
                trySend(getAllVideo())
            }
        }
        contentResolver.registerContentObserver(VIDEO_COLLECTION_URI, true, observer)
        trySend(getAllVideo())
        awaitClose { contentResolver.unregisterContentObserver(observer) }
    }.flowOn(Dispatchers.IO).distinctUntilChanged()

    fun getAllVideo(): ArrayList<Video> {
        val videoEntityList = ArrayList<Video>()

        contentResolver.query(
            VIDEO_COLLECTION_URI,
            projection,
            null,
            null,
            "${MediaStore.Video.Media.SIZE} ASC"
        )?.use { cursor ->
            val idColumnIndex = cursor.getColumnIndex(MediaStore.Video.Media._ID)
            val dataColumnIndex = cursor.getColumnIndex(MediaStore.Video.Media.DATA)
            val dateAddedColumnIndex = cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED)
            val durationColumnIndex = cursor.getColumnIndex(MediaStore.Video.Media.DURATION)
            val sizeColumnIndex = cursor.getColumnIndex(MediaStore.Video.Media.SIZE)
            val titleColumnIndex = cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)
            while (cursor.moveToNext()) {
                val path = cursor.getString(dataColumnIndex)
                if (path != null && File(path).exists()) {
                    val coverUri =
                        ContentUris.withAppendedId(
                            VIDEO_COLLECTION_URI,
                            cursor.getLong(idColumnIndex)
                        )
                    val coverArt = coverUri.toString()
                    val uri = Uri.fromFile(File(path))
                    val videoEntityData = Video(
                        dateAdded = cursor.getInt(dateAddedColumnIndex),
                        duration = cursor.getInt(durationColumnIndex),
                        folderName = "",
                        folderPath = "",
                        formattedDate = "",
                        formattedDuration = "",
                        formattedSize = "",
                        imageUri = coverArt,
                        mediaStoreId = cursor.getLong(idColumnIndex),
                        path = path,
                        size = cursor.getLong(sizeColumnIndex),
                        title = cursor.getString(titleColumnIndex),
                    )
                    videoEntityList.add(videoEntityData)
                }
            }
        }
        return videoEntityList
    }

}
