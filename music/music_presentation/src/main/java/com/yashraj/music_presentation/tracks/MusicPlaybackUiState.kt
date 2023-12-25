package com.yashraj.music_presentation.tracks

import com.yashraj.music_domain.PlayerState
import com.yashraj.music_domain.models.Music
data class MusicPlaybackUiState(
    val playerState: PlayerState? = null,
    val currentMusic: Music? = null,
    val currentPosition: Long = 0L,
    val totalDuration: Long = 0L,
    val isShuffleEnabled: Boolean = false,
    val isRepeatOneEnabled: Boolean = false
)
