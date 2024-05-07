package com.yashraj.music_domain.repository

import com.yashraj.music_domain.models.Folder
import com.yashraj.music_domain.models.Music
import com.yashraj.music_domain.models.Playlist
import kotlinx.coroutines.flow.Flow

interface MusicRepository {

    suspend fun addMusicToFavorites(path: String)

    suspend fun addMusicToPlaylist(music: Music)

    suspend fun getAllMusic(): Flow<List<Music>>

    suspend fun getMusicPlaylists(): Flow<List<Playlist>>

    suspend fun getPlaylistMusic(playlistId: Int): Flow<List<Music>>

    suspend fun createNewPlaylist(playlist: String)

    suspend fun getMusicByPath(path: String): Music?

    suspend fun getFolderTracks(folderPath: String): Flow<List<Music>>

    suspend fun getMusicDirectories(): Flow<List<Folder>>

    suspend fun getMusicFavorites(): Flow<List<Music>>

    suspend fun removeMusicFromFavorites(path: String)

    suspend fun removeMusicFromPlaylist(path: String)

    suspend fun deletePlaylist(playlist: Playlist)

}
