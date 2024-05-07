package com.yashraj.video_data.repository

import com.yashraj.video_domain.models.Folder
import com.yashraj.video_domain.models.Video
import com.yashraj.video_domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeVideoRepository: VideoRepository {

    private val videos = mutableListOf<Video>()
    private val directories = mutableListOf<Folder>()

    override suspend fun getAllVideo(): Flow<List<Video>> {
        return flowOf(videos)
    }

    override suspend fun getVideoByPath(path: String): Video? {
        return videos.find { it.path == path }
    }

    override suspend fun getFolderTracks(folderPath: String): Flow<List<Video>> {
        return flowOf(videos.filter { it.folderPath == folderPath })
    }

    override suspend fun getVideoDirectories(): Flow<List<Folder>> {
        return flowOf(directories)
    }
}