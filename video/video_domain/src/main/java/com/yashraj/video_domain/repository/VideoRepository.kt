package com.yashraj.video_domain.repository

import com.yashraj.video_domain.models.Folder
import com.yashraj.video_domain.models.Video
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    suspend fun getAllVideo(): Flow<List<Video>>

    suspend fun getVideoByPath(path: String): Video?

    suspend fun getFolderTracks(folderPath: String): Flow<List<Video>>

    suspend fun getVideoDirectories(): Flow<List<Folder>>

}
