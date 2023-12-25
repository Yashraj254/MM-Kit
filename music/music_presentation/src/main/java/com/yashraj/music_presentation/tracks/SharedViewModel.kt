package com.yashraj.music_presentation.tracks

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yashraj.music_domain.PlayerState
import com.yashraj.music_domain.models.Music
import com.yashraj.music_domain.usecases.MusicUseCases
import com.yashraj.music_domain.usecases.player_usecases.MusicPlayerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val playerUseCases: MusicPlayerUseCases,
    private val musicUseCases: MusicUseCases
) : ViewModel() {
    var musicPlaybackUiState by mutableStateOf(MusicPlaybackUiState())
        private set
    private val _music = MutableStateFlow<Music?>(null)
    val music: StateFlow<Music?>
        get() = _music

    private var _playerState = MutableStateFlow<PlayerState?>(null)

    init {
        setMediaControllerCallback()
        updatePlayerPosition()
        playerUseCases.getMediaController()
    }

    fun getMusicByPath(path: String) {
        viewModelScope.launch {
            _music.value = musicUseCases.getMusicByPath(path)
        }
    }

    fun currentMusic(): Music? = playerUseCases.getCurrentMusic.invoke()

    private fun setMediaControllerCallback() {
        viewModelScope.launch {
            playerUseCases.setMediaControllerCallback { playerState, currentMusic, currentPosition, totalDuration,
                                                        isShuffleEnabled, isRepeatOneEnabled ->
                musicPlaybackUiState = musicPlaybackUiState.copy(
                    playerState = playerState,
                    currentMusic = currentMusic,
                    currentPosition = currentPosition,
                    totalDuration = totalDuration,
                    isShuffleEnabled = isShuffleEnabled,
                    isRepeatOneEnabled = isRepeatOneEnabled
                )
                _playerState.value = playerState
            }
        }
    }

    private fun updatePlayerPosition() {
        viewModelScope.launch {
            _playerState.collect {
                if (it == PlayerState.PLAYING) {
                    while (_playerState.value == PlayerState.PLAYING) {
                        musicPlaybackUiState = musicPlaybackUiState.copy(
                            currentPosition = playerUseCases.getCurrentMusicPosition.invoke()
                        )
                        delay(1000)
                    }
                }
            }
        }
    }

    fun destroyMediaController() {
        playerUseCases.destroyMediaController()
    }
}