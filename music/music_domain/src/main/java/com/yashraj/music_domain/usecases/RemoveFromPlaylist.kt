package com.yashraj.music_domain.usecases

import com.yashraj.music_domain.repository.MusicRepository

class RemoveFromPlaylist(private val repository: MusicRepository) {
    suspend operator fun invoke(path: String) {
        repository.removeMusicFromPlaylist(path)
    }
}