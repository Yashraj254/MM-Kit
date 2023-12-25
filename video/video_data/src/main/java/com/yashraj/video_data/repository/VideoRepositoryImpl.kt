package com.yashraj.video_data.repository


import com.yashraj.video_data.daos.VideoDirectoryDao
import com.yashraj.video_data.daos.VideoDao
import com.yashraj.video_data.entities.VideoDirectoryEntity
import com.yashraj.video_data.entities.VideoEntity
import com.yashraj.video_data.toFolder
import com.yashraj.video_data.toVideo
import com.yashraj.video_domain.models.Folder
import com.yashraj.video_domain.models.Video
import com.yashraj.video_domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class VideoRepositoryImpl(
    private val videoDao: VideoDao,
    private val directoryDao: VideoDirectoryDao
) : VideoRepository {

    override suspend fun getAllVideo(): Flow<List<Video>> {
        return videoDao.getAllVideo().map { it.map(VideoEntity::toVideo) }
    }

    override suspend fun getVideoByPath(path: String): Video? {
        return videoDao.getVideoByPath(path)?.toVideo()
    }

    override suspend fun getFolderTracks(folderPath: String): Flow<List<Video>> {
        return videoDao.getVideoFromDirectory(folderPath).map { it.map(VideoEntity::toVideo) }
    }

    override suspend fun getVideoDirectories(): Flow<List<Folder>> {
        return directoryDao.getAll().map { it.map(VideoDirectoryEntity::toFolder) }
    }

}
