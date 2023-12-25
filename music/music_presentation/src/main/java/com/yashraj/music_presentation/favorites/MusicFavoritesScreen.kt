package com.yashraj.music_presentation.favorites

import android.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.yashraj.music_presentation.tracks.MusicEvent
import com.yashraj.music_presentation.tracks.MusicPlaybackUiState
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
        musicState = musicState
    )
}

@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun MusicFavoritesScreenPreview() {
//    MusicFavoritesScreen()
}
