package com.yashraj.music_domain.usecases.player_usecases

import com.yashraj.music_domain.service.PlaybackController

class SeekMusicPosition(
    private val playbackController: PlaybackController
) {
    operator fun invoke(position: Long) {
        playbackController.seekTo(position)
    }
}