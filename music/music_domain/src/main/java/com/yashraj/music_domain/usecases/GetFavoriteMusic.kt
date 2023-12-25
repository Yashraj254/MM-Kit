package com.yashraj.music_domain.usecases

import com.yashraj.music_domain.models.Music
import com.yashraj.music_domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow

class GetMusicFavorites(private val repository: MusicRepository) {
    suspend operator fun invoke(): Flow<List<Music>> {
        return repository.getMusicFavorites()
    }
}
