package com.yashraj.music_domain.usecases

import com.yashraj.music_domain.repository.MusicRepository

class RemoveFromFavorites(private val repository: MusicRepository) {
    suspend operator fun invoke(path: String) {
        repository.removeMusicFromFavorites(path)
    }
}