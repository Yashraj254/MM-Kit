package com.yashraj.video_presentation.tracks

import android.util.Log
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
class VideoViewModel @Inject constructor(
    private val videoUseCases: VideoUseCases,
) : ViewModel() {

    private val _videoState = MutableStateFlow(emptyList<Video>())
    val videoState: StateFlow<List<Video>>
        get() = _videoState

    init {
        getAllVideo()
    }

    private fun getAllVideo() {
        viewModelScope.launch {
            videoUseCases.getAllVideo().collect { formattedList ->

                _videoState.value = formattedList
            }
        }
    }

}
