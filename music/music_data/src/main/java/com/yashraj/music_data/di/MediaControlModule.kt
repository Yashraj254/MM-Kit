package com.yashraj.music_data.di

//import com.yashraj.music_data.service.MediaSessionCallback
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.yashraj.music_data.service.MediaSessionCallback
import com.yashraj.music_data.service.MusicPlaybackController
import com.yashraj.music_data.service.MusicService
import com.yashraj.music_domain.service.PlaybackController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MediaControlModule {
    @Provides
    @Singleton
    fun provideAudioAttributes() = AudioAttributes.Builder()
        .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
        .setUsage(C.USAGE_MEDIA)
        .build()


    @Provides
    @Singleton
    @UnstableApi
    fun provideExoPlayer(@ApplicationContext context: Context, audioAttributes: AudioAttributes) =
        ExoPlayer
            .Builder(context)
            .setAudioAttributes(audioAttributes, true)
            .build()

    @Provides
    @Singleton
    fun provideMediaControllerFuture(@ApplicationContext context: Context): ListenableFuture<MediaController> {
        Log.d("TAG", "provideMediaControllerFuture: ")
        val sessionToken = SessionToken(context, ComponentName(context, MusicService::class.java))
        return MediaController.Builder(context, sessionToken).buildAsync()
    }

    @UnstableApi
    @Singleton
    @Provides
    fun provideMediaLibrarySessionCallback() = MediaSessionCallback()



    @Singleton
    @Provides
    fun provideMusicPlaybackController(@ApplicationContext context:Context,listenableFuture: ListenableFuture<MediaController>): PlaybackController {
        return MusicPlaybackController(context,listenableFuture)
    }
}