package com.yashraj.music_presentation.playlists

import androidx.compose.runtime.Composable
import com.yashraj.music_presentation.tracks.MusicState
import com.yashraj.music_presentation.tracks.MusicTracksScreen

@Composable
fun PlaylistTracksScreen(
    showPlayer: (Boolean) -> Unit,
    musicUiState: MusicState,
) {
    MusicTracksScreen(showPlayer = {
        showPlayer(it)
    },  musicState = musicUiState)

}