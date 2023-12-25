package com.yashraj.video_presentation.folders

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yashraj.video_domain.models.Video
import com.yashraj.video_domain.usecases.VideoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoDirectoriesViewModel @Inject constructor(private val videoUseCases: VideoUseCases) :
    ViewModel() {

    private val _state = MutableStateFlow(FolderState())
    val state: StateFlow<FolderState>
        get() = _state

    private val _videosState = MutableStateFlow(emptyList<Video>())
    val videosState: StateFlow<List<Video>>
        get() = _videosState

    init {
        getAllMusicDirectories()
    }

    private fun getAllMusicDirectories() {
        viewModelScope.launch(Dispatchers.IO) {
            videoUseCases.getVideoDirectories.invoke().collect { formattedList ->
                _state.value = FolderState(folders = formattedList)
            }
        }
    }

    fun getFolderTracks(folderPath: String) {
        viewModelScope.launch(Dispatchers.IO) {
            videoUseCases.getFolderTracks.invoke(folderPath).collect {
                Log.d("TAG", "getFolderTracks: $it $folderPath")
                _videosState.value = it
            }
        }
    }
}
