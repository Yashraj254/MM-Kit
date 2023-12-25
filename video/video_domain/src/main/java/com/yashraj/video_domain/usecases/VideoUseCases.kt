package com.yashraj.video_domain.usecases

data class VideoUseCases(
    val getAllVideo: GetAllVideo,
    val getVideoByPath: GetVideoByPath,
    val getVideoDirectories: GetVideoDirectories,
    val getFolderTracks: GetFolderTracks,
)
