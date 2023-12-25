package com.yashraj.music_domain.service

import com.yashraj.music_domain.PlayerState
import com.yashraj.music_domain.models.Music

interface PlaybackController {
    var mediaControllerCallback: (
        (playerState: PlayerState,
         currentMusic: Music?,
         currentPosition: Long,
         totalDuration: Long,
         isShuffleEnabled: Boolean,
         isRepeatOneEnabled: Boolean) -> Unit
    )?

    fun getMediaControllerDataOnDemand()

    fun addMediaItems(musics: List<Music>)

    fun play(mediaItemIndex: Int)

    fun resume()

    fun pause()

    fun seekTo(position: Long)

    fun skipNext()

    fun skipPrevious()

    fun setShuffleModeEnabled(isEnabled: Boolean)

    fun setRepeatOneEnabled(isEnabled: Boolean)

    fun getCurrentMusic(): Music?

    fun getCurrentPosition(): Long

    fun getPlaybackState() : PlayerState?

    fun destroy()
}