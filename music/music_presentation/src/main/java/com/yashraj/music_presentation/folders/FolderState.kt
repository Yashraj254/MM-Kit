package com.yashraj.music_presentation.folders

import com.yashraj.music_domain.models.Folder

data class FolderState(
    var folders: List<Folder> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
)
