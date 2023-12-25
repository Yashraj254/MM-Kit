package com.yashraj.music_domain.usecases.player_usecases

import com.yashraj.music_domain.service.PlaybackController

class ResumeMusic(
    private val playbackController: PlaybackController
) {
    operator fun invoke() {
        playbackController.resume()
    }
}