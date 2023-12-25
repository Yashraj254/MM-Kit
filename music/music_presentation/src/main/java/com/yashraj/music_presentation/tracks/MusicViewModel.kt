package com.yashraj.music_presentation.tracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yashraj.music_domain.models.Music
import com.yashraj.music_domain.usecases.MusicUseCases
import com.yashraj.music_domain.usecases.player_usecases.MusicPlayerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val musicUseCases: MusicUseCases,
    private val playerUseCases: MusicPlayerUseCases
) : ViewModel() {

    private val _musicState = MutableStateFlow(MusicState())
    val musicState: StateFlow<MusicState>
        get() = _musicState

    private val _favoriteMusicState = MutableStateFlow(MusicState())
    val favoriteMusicState: StateFlow<MusicState>
        get() = _favoriteMusicState

    init {
        getAllMusic()
        getFavoriteMusic()
    }

    fun onEvent(event: MusicEvent) {
        when (event) {
            MusicEvent.PlayMusic -> playMusic()

            MusicEvent.ResumeMusic -> playerUseCases.resumeMusic()

            MusicEvent.PauseMusic -> playerUseCases.pauseMusic()

            is MusicEvent.OnMusicSelected -> {
                _musicState.value = _musicState.value.copy(selectedMusic = event.selectedMusic)
            }
        }
    }

    fun playSelectedMusic() {
        playerUseCases.playMusic(0)
    }

    private fun playMusic() {
        _musicState.value.apply {
            this.musics?.indexOf(selectedMusic)?.let {
                playerUseCases.playMusic(it)
            }
        }
    }

    private fun getAllMusic() {
        viewModelScope.launch {
            musicUseCases.getAllMusic().collect { formattedList ->
                _musicState.value = _musicState.value.copy(
                    musics = formattedList
                )
            }
        }
    }

    fun addMediaItems(music: List<Music>) {
        playerUseCases.addMediaItems(music)
    }

    private fun getFavoriteMusic() {
        viewModelScope.launch {
            musicUseCases.getMusicFavorites().collect { formattedList ->
                _favoriteMusicState.value = _favoriteMusicState.value.copy(
                    musics = formattedList
                )
            }
        }
    }

    fun addToFavorites(path: String) {
        viewModelScope.launch {
            musicUseCases.addToFavorites(path)
        }
    }

    fun addToPlaylist(playlistId: Int, music: Music) {
        viewModelScope.launch {
            music.playlistId = playlistId
            musicUseCases.addToPlaylist(music)
        }
    }

    fun removeFromFavorites(path: String) {
        viewModelScope.launch {
            musicUseCases.removeFromFavorites(path)
        }
    }

    fun isAddedToFavorites(path: String): Boolean {
        val isAddedToFav = _favoriteMusicState.value.musics?.filter {
            it.path == path
        }
        return !isAddedToFav.isNullOrEmpty()
    }
}
