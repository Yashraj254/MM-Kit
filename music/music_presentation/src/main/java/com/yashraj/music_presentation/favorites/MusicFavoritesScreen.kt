package com.yashraj.music_presentation.favorites

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.yashraj.music_presentation.tracks.MusicState
import com.yashraj.music_presentation.tracks.MusicTracksScreen

@Composable
fun MusicFavoritesScreen(
    showPlayer: (Boolean) -> Unit,
    musicState: MusicState
) {
    MusicTracksScreen(
        showPlayer = {
            showPlayer(it)
        },
        isPlaylistTracks = false,
        isFavoriteTracks = true,
        musicState = musicState
    )
}

@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun MusicFavoritesScreenPreview() {
//    MusicFavoritesScreen()
}
