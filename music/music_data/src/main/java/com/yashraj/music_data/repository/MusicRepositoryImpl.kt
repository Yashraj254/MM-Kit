package com.yashraj.music_data.repository

import com.yashraj.music_data.daos.DirectoryDao
import com.yashraj.music_data.daos.MusicDao
import com.yashraj.music_data.daos.PlaylistDao
import com.yashraj.music_data.entities.DirectoryEntity
import com.yashraj.music_data.entities.MusicEntity
import com.yashraj.music_data.entities.PlaylistEntity
import com.yashraj.music_data.toFolder
import com.yashraj.music_data.toMusic
import com.yashraj.music_data.toMusicEntity
import com.yashraj.music_data.toPlaylist
import com.yashraj.music_data.toPlaylistEntity
import com.yashraj.music_domain.models.Folder
import com.yashraj.music_domain.models.Music
import com.yashraj.music_domain.models.Playlist
import com.yashraj.music_domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MusicRepositoryImpl(
    private val musicDao: MusicDao,
    private val directoryDao: DirectoryDao,
    private val playlistDao: PlaylistDao
) : MusicRepository {
    override suspend fun addMusicToFavorites(path: String) {
        musicDao.addToFavorites(path)
    }

    override suspend fun addMusicToPlaylist(music: Music) {
        musicDao.upsertMusic(music.toMusicEntity())
    }

    override suspend fun createNewPlaylist(playlist: String) {
        playlistDao.upsert(PlaylistEntity(playlist = playlist))
    }

    override suspend fun getAllMusic(): Flow<List<Music>> {
        return musicDao.getAllMusic().map { it.map(MusicEntity::toMusic) }
    }

    override suspend fun getMusicByPath(path: String): Music? {
        return musicDao.getMusicByPath(path)?.toMusic()
    }

    override suspend fun getFolderTracks(folderPath: String): Flow<List<Music>> {
        return musicDao.getMusicFromDirectory(folderPath).map { it.map(MusicEntity::toMusic) }
    }

    override suspend fun getMusicDirectories(): Flow<List<Folder>> {
        return directoryDao.getAll().map { it.map(DirectoryEntity::toFolder) }
    }

    override suspend fun getMusicPlaylists(): Flow<List<Playlist>> {
        return playlistDao.getAllPlaylists().map { it.map(PlaylistEntity::toPlaylist) }
    }

    override suspend fun getPlaylistMusic(playlistId: Int): Flow<List<Music>> {
        return musicDao.getPlaylistMusic(playlistId).map { it.map(MusicEntity::toMusic) }
    }

    override suspend fun getMusicFavorites(): Flow<List<Music>> {
        return musicDao.getMusicFavorites().map { it.map(MusicEntity::toMusic) }
    }

    override suspend fun removeMusicFromFavorites(path: String) {
        musicDao.removeFromFavorites(path)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        playlistDao.deletePlaylist(playlist.toPlaylistEntity())
    }

}
