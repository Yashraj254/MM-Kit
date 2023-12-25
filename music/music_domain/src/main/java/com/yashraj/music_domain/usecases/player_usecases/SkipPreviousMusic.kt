package com.yashraj.music_domain.usecases.player_usecases

import com.yashraj.music_domain.service.PlaybackController

class SkipPreviousMusic(
    private val playbackController: PlaybackController
) {
    operator fun invoke() {
        playbackController.skipPrevious()
    }
}