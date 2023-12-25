package com.yashraj.music_domain.usecases.player_usecases

import com.yashraj.music_domain.PlayerState
import com.yashraj.music_domain.models.Music
import com.yashraj.music_domain.service.PlaybackController

class GetMediaController(
    private val playbackController: PlaybackController
) {
    operator fun invoke() {
        playbackController.getMediaControllerDataOnDemand()
    }
}