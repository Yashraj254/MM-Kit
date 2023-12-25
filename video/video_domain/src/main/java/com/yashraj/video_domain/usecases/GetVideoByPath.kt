package com.yashraj.video_domain.usecases

import com.yashraj.video_domain.models.Video
import com.yashraj.video_domain.repository.VideoRepository

class GetVideoByPath(private val repository: VideoRepository) {
    suspend operator fun invoke(path: String): Video? {
        return repository.getVideoByPath(path)
    }
}