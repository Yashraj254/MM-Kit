package com.yashraj.music_presentation.folders

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yashraj.music_domain.usecases.MusicUseCases
import com.yashraj.music_presentation.tracks.MusicState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicDirectoriesViewModel @Inject constructor(private val musicUseCases: MusicUseCases) : ViewModel() {

    private val _state = MutableStateFlow(FolderState())
    val state: StateFlow<FolderState>
        get() = _state

    private val _musicState = MutableStateFlow(MusicState())
    val musicState: StateFlow<MusicState>
        get() = _musicState

    init {
        getAllMusicDirectories()
    }

    private fun getAllMusicDirectories() {
        viewModelScope.launch(Dispatchers.IO) {
            musicUseCases.getMusicDirectories.invoke().collect { formattedList ->
                _state.value = FolderState(folders = formattedList)
            }
        }
    }

    fun getFolderTracks(folderPath: String){
        viewModelScope.launch(Dispatchers.IO) {
            musicUseCases.getFolderTracks.invoke(folderPath).collect{
                Log.d("TAG", "getFolderTracks: $it $folderPath")
                _musicState.value = MusicState(musics = it)
            }
        }
    }
}
