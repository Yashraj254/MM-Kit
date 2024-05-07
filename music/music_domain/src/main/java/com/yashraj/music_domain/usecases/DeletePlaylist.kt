package com.yashraj.music_domain.usecases

import com.yashraj.music_domain.models.Playlist
import com.yashraj.music_domain.repository.MusicRepository

class DeletePlaylist(private val repository: MusicRepository) {
    suspend operator fun invoke(playlist: Playlist) {
        repository.deletePlaylist(playlist)
    }
}