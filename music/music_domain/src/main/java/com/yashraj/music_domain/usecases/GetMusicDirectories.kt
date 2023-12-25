package com.yashraj.music_domain.usecases

import com.yashraj.music_domain.models.Folder
import com.yashraj.music_domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow

class GetMusicDirectories(private val repository: MusicRepository) {

    suspend operator fun invoke(): Flow<List<Folder>> {
        return  repository.getMusicDirectories()
    }
}
