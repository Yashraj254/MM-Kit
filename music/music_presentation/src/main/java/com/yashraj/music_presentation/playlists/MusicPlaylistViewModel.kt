package com.yashraj.music_presentation.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yashraj.music_domain.models.Playlist
import com.yashraj.music_domain.usecases.MusicUseCases
import com.yashraj.music_presentation.tracks.MusicState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicPlaylistViewModel @Inject constructor(private val musicUseCases: MusicUseCases) :
    ViewModel() {

    private val _playlistState = MutableStateFlow(PlaylistState())
    val playlistState: StateFlow<PlaylistState>
        get() = _playlistState

    private val _musicState = MutableStateFlow(MusicState())
    val musicState: StateFlow<MusicState>
        get() = _musicState

    init {
        getAllMusicPlaylists()
    }

    private fun getAllMusicPlaylists() {
        viewModelScope.launch(Dispatchers.IO) {
            musicUseCases.getMusicPlaylists().collect { formattedList ->
                _playlistState.value = PlaylistState(playlists = formattedList)
            }
        }
    }

    fun createNewPlaylist(playlistName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            musicUseCases.createNewPlaylist(playlistName)
        }
    }

    fun getPlaylistTracks(playlistId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            musicUseCases.getPlaylistMusic(playlistId).collect {
                _musicState.value = MusicState(musics = it)
            }
        }
    }

    fun deletePlaylist(playlist: Playlist){
        viewModelScope.launch(Dispatchers.IO) {
            musicUseCases.deletePlaylist(playlist)
        }
    }
}
