package com.yashraj.music_data.di

import android.content.ContentResolver
import android.content.Context
import android.util.Log
import androidx.media3.session.MediaController
import com.google.common.util.concurrent.ListenableFuture
import com.yashraj.music_data.MusicDataSource
import com.yashraj.music_data.MusicSynchronizer
import com.yashraj.music_data.daos.DirectoryDao
import com.yashraj.music_data.daos.MusicDao
import com.yashraj.music_data.daos.PlaylistDao
import com.yashraj.music_data.service.MusicPlaybackController
import com.yashraj.music_data.repository.MusicRepositoryImpl
import com.yashraj.music_domain.repository.MusicRepository
import com.yashraj.music_domain.service.PlaybackController
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMusicDataSource(
        contentResolver: ContentResolver,
        musicDao: MusicDao,
        directoryDao: DirectoryDao
    ): MusicDataSource {
        return MusicDataSource(contentResolver,musicDao, directoryDao)
    }

    @Singleton
    @Provides
    fun provideMusicRepository(musicDao: MusicDao,directoryDao: DirectoryDao,playlistDao: PlaylistDao): MusicRepository {
        return MusicRepositoryImpl(musicDao,directoryDao,playlistDao)
    }



}

@Module
@InstallIn(SingletonComponent::class)
interface MediaModule {

    @Binds
    @Singleton
    fun bindsMediaSynchronizer(
        mediaSynchronizer: MusicDataSource
    ): MusicSynchronizer
}
