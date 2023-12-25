package com.yashraj.music_domain.usecases

import com.yashraj.music_domain.models.Music
import com.yashraj.music_domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow

class GetPlaylistMusic(private val repository: MusicRepository) {
    suspend operator fun invoke(playlistId: Int): Flow<List<Music>> {
        return repository.getPlaylistMusic(playlistId)
    }
}