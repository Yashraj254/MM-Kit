package com.yashraj.mmkit.di

import android.content.Context
import androidx.room.Room
import com.yashraj.mmkit.MediaDatabase
import com.yashraj.music_data.daos.DirectoryDao
import com.yashraj.music_data.daos.MusicDao
import com.yashraj.music_data.daos.PlaylistDao
import com.yashraj.video_data.daos.VideoDao
import com.yashraj.video_data.daos.VideoDirectoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMediaDatabase(
        @ApplicationContext context: Context
    ): MediaDatabase = Room.databaseBuilder(
        context,
        MediaDatabase::class.java,
        MediaDatabase.DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideMediumDao(db: MediaDatabase): MusicDao = db.musicDao()

    @Singleton
    @Provides
    fun provideVideoDao(db: MediaDatabase): VideoDao = db.videoDao()

    @Singleton
    @Provides
    fun provideDirectoryDao(db: MediaDatabase): DirectoryDao = db.directoryDao()

    @Singleton
    @Provides
    fun providePlaylistDao(db: MediaDatabase): PlaylistDao = db.playlistDao()

     @Singleton
    @Provides
    fun provideVideoDirectoryDao(db: MediaDatabase): VideoDirectoryDao = db.videoDirectoryDao()



}
