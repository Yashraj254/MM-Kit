package com.yashraj.music_data.service

import android.content.Intent
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.system.exitProcess

@AndroidEntryPoint
class MusicService : MediaSessionService() {

    @Inject
    lateinit var mediaSession: MediaSession

    @UnstableApi
    override fun onCreate() {
        super.onCreate()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo) = mediaSession

    override fun onTaskRemoved(rootIntent: Intent?) {
        stopService()
        super.onTaskRemoved(rootIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        exitProcess(0)
    }

    private fun stopService() {
        mediaSession.run {
            player.release()
            release()
        }
        stopSelf()
    }

}