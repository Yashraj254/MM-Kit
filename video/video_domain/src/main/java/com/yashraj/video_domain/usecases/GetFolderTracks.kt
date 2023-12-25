package com.yashraj.video_domain.usecases

import com.yashraj.video_domain.models.Video
import com.yashraj.video_domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow

class GetFolderTracks(private val repository: VideoRepository) {
    suspend operator fun invoke(folderPath: String): Flow<List<Video>> {
        return repository.getFolderTracks(folderPath)
    }
}
