package com.yashraj.music_domain.usecases

import com.yashraj.music_domain.repository.MusicRepository

class AddToFavorites(private val repository: MusicRepository) {
    suspend operator fun invoke(path: String) {
        repository.addMusicToFavorites(path)
    }
}