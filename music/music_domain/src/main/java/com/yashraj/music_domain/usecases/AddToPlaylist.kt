package com.yashraj.music_domain.usecases

import com.yashraj.music_domain.models.Music
import com.yashraj.music_domain.repository.MusicRepository

class AddToPlaylist(private val repository: MusicRepository) {
    suspend operator fun invoke(music: Music) {
        return repository.addMusicToPlaylist(music)
    }
}