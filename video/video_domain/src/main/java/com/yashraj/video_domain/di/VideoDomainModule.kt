package com.yashraj.video_domain.di

import com.yashraj.video_domain.repository.VideoRepository
import com.yashraj.video_domain.usecases.GetAllVideo
import com.yashraj.video_domain.usecases.GetFolderTracks
import com.yashraj.video_domain.usecases.GetVideoByPath
import com.yashraj.video_domain.usecases.GetVideoDirectories
import com.yashraj.video_domain.usecases.VideoUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VideoDomainModule {

    @Singleton
    @Provides
    fun provideVideoUseCases(
        repository: VideoRepository
    ): VideoUseCases {
        return VideoUseCases(
            getAllVideo = GetAllVideo(repository),
            getVideoByPath = GetVideoByPath(repository),
            getVideoDirectories = GetVideoDirectories(repository),
            getFolderTracks = GetFolderTracks(repository),
        )
    }

}
