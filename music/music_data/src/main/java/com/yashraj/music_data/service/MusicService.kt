package com.yashraj.music_data.service

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionCommand
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.yashraj.music_data.R
import com.yashraj.music_domain.usecases.player_usecases.MusicPlayerUseCases
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.system.exitProcess

@AndroidEntryPoint
class MusicService : MediaSessionService() {


    @Inject
    lateinit var mediaSession: MediaSession

    @UnstableApi
    override fun onCreate() {
        Log.d("TAG", "Service started ")
        super.onCreate()

    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo) = mediaSession

    override fun onTaskRemoved(rootIntent: Intent?) {
       stopService()
        super.onTaskRemoved(rootIntent)
    }



    override fun onDestroy() {
        Log.d("TAG", "onDestroy: Stop Service")

        super.onDestroy()
        exitProcess(0)

    }


    private fun stopService(){
        mediaSession.run {
            player.release()
            release()
        }
        stopSelf()
    }

}