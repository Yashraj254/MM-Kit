package com.yashraj.video_presentation.folders

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.yashraj.video_domain.models.Video
import com.yashraj.video_presentation.tracks.VideoTracksScreen

@Composable
fun FolderTracksScreen(videos: List<Video>) {
    VideoTracksScreen(videos)
}