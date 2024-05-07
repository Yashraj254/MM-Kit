package com.yashraj.music_presentation.folders

import androidx.compose.runtime.Composable
import com.yashraj.music_presentation.tracks.MusicState
import com.yashraj.music_presentation.tracks.MusicTracksScreen

@Composable
fun FolderTracksScreen(
    showPlayer: (Boolean) -> Unit,
    musicUiState: MusicState,
) {
    MusicTracksScreen(showPlayer = {
        showPlayer(it)
    },
        isPlaylistTracks = false,
        isFavoriteTracks = false,
        musicState = musicUiState)

}