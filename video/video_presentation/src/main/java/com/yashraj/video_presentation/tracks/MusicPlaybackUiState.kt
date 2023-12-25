package com.yashraj.video_presentation.tracks

import com.yashraj.video_domain.PlayerState
import com.yashraj.video_domain.models.Video

data class MusicPlaybackUiState(
    val playerState: PlayerState? = null,
    val currentMusic: Video? = null,
    val currentPosition: Long = 0L,
    val totalDuration: Long = 0L,
    val isShuffleEnabled: Boolean = false,
    val isRepeatOneEnabled: Boolean = false
)
