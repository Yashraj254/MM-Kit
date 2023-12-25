package com.yashraj.music_domain.usecases

import com.yashraj.music_domain.models.Music
import com.yashraj.music_domain.repository.MusicRepository

class GetMusicByPath(private val repository: MusicRepository) {
    suspend operator fun invoke(path: String): Music? {
        return repository.getMusicByPath(path)
    }
}