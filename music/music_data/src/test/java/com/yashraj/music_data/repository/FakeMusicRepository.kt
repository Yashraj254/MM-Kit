package com.yashraj.music_data.repository

import com.yashraj.music_domain.models.Folder
import com.yashraj.music_domain.models.Music
import com.yashraj.music_domain.models.Playlist
import com.yashraj.music_domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeMusicRepository: MusicRepository {

    private val music = mutableListOf<Music>()
    private val playlists = mutableListOf<Playlist>()
    private val folders = mutableListOf<Folder>()


    override suspend fun addMusicToFavorites(path: String) {
        music.find { it.path == path }?.favorite = 1
    }

    override suspend fun addMusicToPlaylist(music: Music) {
        val musicToAdd = this.music.find { it.path == music.path }
        musicToAdd?.let {
            it.playlistId = music.playlistId
        }
    }

    override suspend fun getAllMusic(): Flow<List<Music>> {
        return flowOf(music)
    }

    override suspend fun getMusicPlaylists(): Flow<List<Playlist>> {
        return flowOf(playlists)
    }

    override suspend fun getPlaylistMusic(playlistId: Int): Flow<List<Music>> {
        return flowOf(music.filter { it.playlistId == playlistId })
    }

    override suspend fun createNewPlaylist(playlist: String) {
        playlists.add(Playlist( 0,playlist))
    }

    override suspend fun getMusicByPath(path: String): Music? {
        return music.find { it.path == path }
    }

    override suspend fun getFolderTracks(folderPath: String): Flow<List<Music>> {
        return flowOf(music.filter { it.folderPath == folderPath })
    }

    override suspend fun getMusicDirectories(): Flow<List<Folder>> {
        return flowOf(folders)
    }

    override suspend fun getMusicFavorites(): Flow<List<Music>> {
        return flowOf(music.filter { it.favorite == 1 })
    }

    override suspend fun removeMusicFromFavorites(path: String) {
        music.find { it.path == path }?.favorite = 0
    }

    override suspend fun removeMusicFromPlaylist(path: String) {
        music.find { it.path == path }?.playlistId = 0
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        playlists.remove(playlist)
    }


}