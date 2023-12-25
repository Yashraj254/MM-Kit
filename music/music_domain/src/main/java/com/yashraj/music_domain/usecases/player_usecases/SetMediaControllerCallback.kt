package com.yashraj.music_domain.usecases.player_usecases

import com.yashraj.music_domain.PlayerState
import com.yashraj.music_domain.service.PlaybackController
import com.yashraj.music_domain.models.Music

class SetMediaControllerCallback(
    private val playbackController: PlaybackController
) {
    operator fun invoke(
        callback: (
            playerState: PlayerState,
            currentMusic: Music?,
            currentPosition: Long,
            totalDuration: Long,
            isShuffleEnabled: Boolean,
            isRepeatOneEnabled: Boolean
        ) -> Unit
    ) {
        playbackController.mediaControllerCallback = callback
    }
}