package com.yashraj.music_data

import android.content.ContentResolver
import android.content.ContentUris
import android.database.ContentObserver
import android.net.Uri
import android.provider.MediaStore
import android.provider.MediaStore.Audio
import android.util.Log
import com.google.common.base.CharMatcher.`is`
import com.yashraj.music_data.daos.DirectoryDao
import com.yashraj.music_data.daos.MusicDao
import com.yashraj.music_data.entities.DirectoryEntity
import com.yashraj.music_data.entities.MusicEntity
import com.yashraj.music_domain.models.Music
import com.yashraj.utils.MUSIC_COLLECTION_URI
import com.yashraj.utils.artworkUri
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


class MusicDataSource @Inject constructor(
    private val contentResolver: ContentResolver,
    private val musicDao: MusicDao,
    private val directoryDao: DirectoryDao,
) : MusicSynchronizer {

    private var musicSyncingJob: Job? = null

    private val projection = arrayOf(
        Audio.Media.ALBUM,
        Audio.Media.ALBUM_ID,
        Audio.Media.ARTIST,
        Audio.Media.ARTIST_ID,
        Audio.Media.DATE_ADDED,
        Audio.Media.DATA,
        Audio.Media.DURATION,
        Audio.Media._ID,
        Audio.Media.SIZE,
        Audio.Media.TITLE,
        Audio.Media.TRACK,
        Audio.Media.YEAR,
        MediaStore.Images.Media._ID
    )

    override fun startSync() {
        if (musicSyncingJob != null) return
        musicSyncingJob = getMusicFlow().onEach { music ->
            CoroutineScope(Dispatchers.IO).launch { updateMusicDirectories(music) }
            CoroutineScope(Dispatchers.IO).launch { updateMusic(music) }
        }.launchIn(CoroutineScope(SupervisorJob() + Dispatchers.IO))
    }

    override fun stopSync() {
        musicSyncingJob?.cancel()
    }

    private suspend fun updateMusicDirectories(music: List<Music>) = withContext(
        Dispatchers.Default
    ) {
        val directories = music.groupBy { File(it.path).parentFile!! }.map { (file, musics) ->
            DirectoryEntity(
                path = file.path,
                name = file.name,
                mediaCount = music.size,
                size = musics.sumOf {
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

    private suspend fun updateMusic(music: List<Music>) = withContext(Dispatchers.IO) {
        val musicEntities = music.map {
            val file = File(it.path)
            val musicEntity = musicDao.getMusicByPath(it.path)
            musicEntity ?: MusicEntity(
                album = it.album,
                albumId = it.albumId,
                artist = it.artist,
                artistId = it.artistId,
                dateAdded = it.dateAdded,
                duration = it.duration,
                favorite = it.favorite,
                folderName = it.folderName,
                folderPath = file.parent!!,
                imageUri = it.imageUri,
                mediaStoreId = it.mediaStoreId,
                path = it.path,
                playlistId = it.playlistId,
                size = it.size,
                title = it.title,
            )
        }

        musicDao.upsertAllMusic(musicEntities)

        val currentMediaPaths = musicEntities.map {
            it.path
        }

        val unwantedMedia = musicDao.getAllMusic().first()
            .filterNot { it.path in currentMediaPaths }

        val unwantedMediaPaths = unwantedMedia.map { it.path }

        musicDao.delete(unwantedMediaPaths)

    }

    private fun getMusicFlow(): Flow<List<Music>> = callbackFlow {
        val observer = object : ContentObserver(null) {
            override fun onChange(selfChange: Boolean) {
                trySend(getAllMusic())
            }
        }
        contentResolver.registerContentObserver(MUSIC_COLLECTION_URI, true, observer)
        trySend(getAllMusic())
        awaitClose { contentResolver.unregisterContentObserver(observer) }
    }.flowOn(Dispatchers.IO).distinctUntilChanged()

    private fun getAllMusic(): ArrayList<Music> {
        val musicList = ArrayList<Music>()

        contentResolver.query(
            MUSIC_COLLECTION_URI,
            projection,
            null,
            null,
            "${Audio.Media.DISPLAY_NAME} ASC"
        )?.use { cursor ->
            val idColumnIndex = cursor.getColumnIndex(Audio.Media._ID)
            val albumColumnIndex = cursor.getColumnIndex(Audio.Media.ALBUM)
            val albumIdColumnIndex = cursor.getColumnIndex(Audio.Media.ALBUM_ID)
            val artistColumnIndex = cursor.getColumnIndex(Audio.Media.ARTIST)
            val artistIdColumnIndex = cursor.getColumnIndex(Audio.Media.ARTIST_ID)
            val dataColumnIndex = cursor.getColumnIndex(Audio.Media.DATA)
            val dateAddedColumnIndex = cursor.getColumnIndex(Audio.Media.DATE_ADDED)
            val durationColumnIndex = cursor.getColumnIndex(Audio.Media.DURATION)
            val sizeColumnIndex = cursor.getColumnIndex(Audio.Media.SIZE)
            val titleColumnIndex = cursor.getColumnIndex(Audio.Media.TITLE)
            while (cursor.moveToNext()) {
//                Log.d("TAG", "${cursor.getString(titleColumnIndex)} \n ${cursor.getColumnIndex(MediaStore.Images.Media.SIZE)}")
                val path = cursor.getString(dataColumnIndex)
                val artist = cursor.getString(artistColumnIndex)
                if (path != null && artist != null && File(path).exists()) {
                    val coverUri =
                        ContentUris.withAppendedId(artworkUri, cursor.getLong(albumIdColumnIndex))

                    var coverArt = coverUri.toString()
                    try {
                        contentResolver.openInputStream(coverUri)?.close()
//                        Log.d("TAG", "${cursor.getString(titleColumnIndex)}\n $coverArt \n cover art exists: ")
                    } catch (e: Exception) {
//                        Log.d("TAG", "Exception $e")
                        coverArt = ""
//                        Log.d("TAG", "${cursor.getString(titleColumnIndex)}\n $coverArt \n cover art not exists: ")
                    }


                    Uri.fromFile(File(path))
                    val music = Music(
                        album = cursor.getString(albumColumnIndex),
                        albumId = cursor.getLong(albumIdColumnIndex),
                        artist = artist,
                        artistId = cursor.getLong(artistIdColumnIndex),
                        dateAdded = cursor.getInt(dateAddedColumnIndex),
                        duration = cursor.getInt(durationColumnIndex),
                        favorite = 0,
                        folderName = "",
                        folderPath = "",
                        formattedDate = "",
                        formattedDuration = "",
                        formattedSize = "",
                        imageUri = coverArt,
                        mediaStoreId = cursor.getLong(idColumnIndex),
                        path = path,
                        playlistId = 0,
                        size = cursor.getInt(sizeColumnIndex),
                        title = cursor.getString(titleColumnIndex),
                    )
                    musicList.add(music)
                }
            }
        }
        return musicList
    }

}
