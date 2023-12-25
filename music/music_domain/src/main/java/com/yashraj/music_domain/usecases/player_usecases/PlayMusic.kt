package com.yashraj.music_domain.usecases.player_usecases

import android.util.Log
import com.yashraj.music_domain.service.PlaybackController

class PlayMusic(
    private val playbackController: PlaybackController
) {
    operator fun invoke(mediaItemsIndex: Int) {
        playbackController.play(mediaItemsIndex)
    }
}