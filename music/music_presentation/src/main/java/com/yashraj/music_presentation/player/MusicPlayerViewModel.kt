package com.yashraj.music_presentation.player

import androidx.lifecycle.ViewModel
import com.yashraj.music_domain.usecases.player_usecases.MusicPlayerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    private val playerUseCases: MusicPlayerUseCases
) : ViewModel() {
    fun onEvent(event: MusicPlayerEvent) {
        when (event) {
            MusicPlayerEvent.ResumeMusic -> playerUseCases.resumeMusic()

            MusicPlayerEvent.PauseMusic -> playerUseCases.pauseMusic()

            MusicPlayerEvent.SkipNextMusic -> playerUseCases.skipNextMusic()

            MusicPlayerEvent.SkipPreviousMusic -> playerUseCases.skipPreviousMusic()

            is MusicPlayerEvent.SeekMusicPosition -> playerUseCases.seekMusicPosition(event.musicPosition)

            is MusicPlayerEvent.SetMusicShuffleEnabled -> playerUseCases.setMusicShuffleEnabled(event.isShuffleEnabled)

            is MusicPlayerEvent.SetPlayerRepeatOneEnabled -> playerUseCases.setPlayerRepeatOneEnabled(event.isRepeatOneEnabled)
        }
    }
}