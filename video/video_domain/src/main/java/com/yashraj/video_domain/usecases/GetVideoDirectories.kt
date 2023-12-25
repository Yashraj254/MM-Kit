package com.yashraj.video_domain.usecases

import com.yashraj.video_domain.models.Folder
import com.yashraj.video_domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow

class GetVideoDirectories(private val repository: VideoRepository) {

    suspend operator fun invoke(): Flow<List<Folder>> {
        return  repository.getVideoDirectories()
    }
}
