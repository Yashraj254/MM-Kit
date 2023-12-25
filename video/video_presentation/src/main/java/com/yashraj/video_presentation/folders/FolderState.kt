package com.yashraj.video_presentation.folders

import com.yashraj.video_domain.models.Folder

data class FolderState(
    var folders: List<Folder> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
)
