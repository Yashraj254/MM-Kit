package com.yashraj.video_presentation.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yashraj.video_domain.models.Video
import com.yashraj.video_domain.usecases.VideoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val videoUseCases: VideoUseCases,
) : ViewModel() {

    private val _videoState = MutableStateFlow(emptyList<Video>())
    val videoState: StateFlow<List<Video>>
        get() = _videoState


    fun getVideosByPath(path: String) {
        viewModelScope.launch {
            videoUseCases.getFolderTracks(path).collect { formattedList ->
                _videoState.value = formattedList
            }
        }
    }

    fun getAllVideos() {
        viewModelScope.launch {
            videoUseCases.getAllVideo().collect { formattedList ->
                _videoState.value = formattedList
            }
        }
    }
}
