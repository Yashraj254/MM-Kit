package com.yashraj.music_presentation.folders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.yashraj.music_presentation.components.MusicItem
import com.yashraj.music_presentation.tracks.MusicEvent
import com.yashraj.music_presentation.tracks.MusicState
import com.yashraj.music_presentation.tracks.MusicTracksScreen

@Composable
fun FolderTracksScreen(
    showPlayer: (Boolean) -> Unit,
    musicUiState: MusicState,
) {
    MusicTracksScreen(showPlayer = {
        showPlayer(it)
    },  musicState = musicUiState)

}