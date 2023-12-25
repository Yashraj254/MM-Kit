package com.yashraj.music_domain.usecases

import com.yashraj.music_domain.models.Music
import com.yashraj.music_domain.models.Playlist
import com.yashraj.music_domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow

class CreateNewPlaylist(private val repository: MusicRepository) {
    suspend operator fun invoke(playlist: String) {
        return repository.createNewPlaylist(playlist)
    }
}