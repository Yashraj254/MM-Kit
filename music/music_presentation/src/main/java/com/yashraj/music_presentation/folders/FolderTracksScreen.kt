package com.yashraj.music_presentation.folders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yashraj.music_presentation.components.MusicItem
import com.yashraj.music_presentation.components.MusicMiniPlayerCard
import com.yashraj.music_presentation.tracks.MusicEvent
import com.yashraj.music_presentation.tracks.MusicPlaybackUiState
import com.yashraj.music_presentation.tracks.MusicState

@Composable
fun FolderTracksScreen(
    onEvent: (MusicEvent) -> Unit,
    musicUiState: MusicState,
    musicPlaybackUiState: MusicPlaybackUiState,
    onNavigateToMusicPlayer: () -> Unit
) {
    val musics = musicUiState.musics
//    val onEvent = viewModel::onEvent


    Box {
        if (musics != null)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(musics.size) { i ->
                    val music = musics[i]
                    MusicItem(
                        music = music,
                        imageVector = Icons.Default.MusicNote,
                        tint = Color.Unspecified,
                        onClick = {
                            onEvent(MusicEvent.OnMusicSelected(music))
                            onEvent(MusicEvent.PlayMusic)
                        }
                    )

                }
            }
        with(musicPlaybackUiState) {
            if (playerState != com.yashraj.music_domain.PlayerState.STOPPED) {
                MusicMiniPlayerCard(
                    modifier = androidx.compose.ui.Modifier
                        .padding(10.dp)
                        .align(androidx.compose.ui.Alignment.BottomCenter),
                    music = currentMusic,
                    playerState = playerState,
                    onResumeClicked = { onEvent(com.yashraj.music_presentation.tracks.MusicEvent.ResumeMusic) },
                    onPauseClicked = { onEvent(com.yashraj.music_presentation.tracks.MusicEvent.PauseMusic) },
                    onClick = {
                        onNavigateToMusicPlayer()
                    }
                )
            }
        }
    }
}