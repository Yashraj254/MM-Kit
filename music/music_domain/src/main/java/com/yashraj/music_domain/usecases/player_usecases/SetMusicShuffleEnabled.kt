package com.yashraj.music_domain.usecases.player_usecases

import com.yashraj.music_domain.service.PlaybackController

class SetMusicShuffleEnabled(
    private val playbackController: PlaybackController
) {
    operator fun invoke(isEnabled: Boolean) {
        playbackController.setShuffleModeEnabled(isEnabled)
    }
}