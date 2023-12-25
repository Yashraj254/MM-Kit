package com.yashraj.music_domain.usecases

import com.yashraj.music_domain.models.Playlist
import com.yashraj.music_domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow

class GetMusicPlaylists(private val repository: MusicRepository) {
    suspend operator fun invoke(): Flow<List<Playlist>> {
        return repository.getMusicPlaylists()
    }
}
