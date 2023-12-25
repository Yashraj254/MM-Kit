package com.yashraj.music_domain.usecases.player_usecases

import com.yashraj.music_domain.models.Music
import com.yashraj.music_domain.service.PlaybackController

class AddMediaItems(
    private val playbackController: PlaybackController
) {
    operator fun invoke(musics: List<Music>) {
        playbackController.addMediaItems(musics)
    }
}