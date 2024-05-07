package com.yashraj.database

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.session.MediaSession
import androidx.room.Room
import com.yashraj.database.daos.DirectoryDao
import com.yashraj.database.daos.MusicDao
import com.yashraj.database.daos.PlaylistDao
import com.yashraj.database.daos.VideoDao
import com.yashraj.database.daos.VideoDirectoryDao
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
