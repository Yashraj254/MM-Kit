package com.yashraj.video_data.di

import android.content.ContentResolver
import com.yashraj.video_data.VideoDataSource
import com.yashraj.video_data.VideoSynchronizer
import com.yashraj.database.daos.VideoDirectoryDao
import com.yashraj.database.daos.VideoDao
import com.yashraj.video_data.repository.VideoRepositoryImpl
import com.yashraj.video_domain.repository.VideoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideVideoDataSource(
        contentResolver: ContentResolver,
        videoDao: com.yashraj.database.daos.VideoDao,
        directoryDao: com.yashraj.database.daos.VideoDirectoryDao
    ): VideoDataSource {
        return VideoDataSource(contentResolver,videoDao, directoryDao)
    }

    @Singleton
    @Provides
    fun provideVideoRepository(videoDao: com.yashraj.database.daos.VideoDao, directoryDao: com.yashraj.database.daos.VideoDirectoryDao): VideoRepository {
        return VideoRepositoryImpl(videoDao,directoryDao)
    }

}

@Module
@InstallIn(SingletonComponent::class)
interface MediaModule {

    @Binds
    @Singleton
    fun bindsMediaSynchronizer(
        mediaSynchronizer: VideoDataSource
    ): VideoSynchronizer
}
