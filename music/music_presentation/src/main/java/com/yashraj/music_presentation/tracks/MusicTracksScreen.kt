package com.yashraj.music_presentation.tracks

import android.graphics.Color
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yashraj.music_domain.models.Music
import com.yashraj.music_presentation.components.MusicItem


@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun MusicTracksScreen(
    showPlayer: (Boolean) -> Unit,
    musicState: MusicState,
    isPlaylistTracks: Boolean,
    isFavoriteTracks: Boolean,
    musicViewModel: MusicViewModel = hiltViewModel(),
) {

    val musics = musicState.musics

    if (musics != null)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(musics, key = { item: Music -> item.path }) { music ->
                val dismissState = rememberDismissState()

                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    if (isPlaylistTracks)
                        musicViewModel.removeFromPlaylist(music)
                    else if (isFavoriteTracks)
                        musicViewModel.removeFromFavorites(music.path)
                }
                if (isPlaylistTracks || isFavoriteTracks)
                    SwipeToDismiss(
                        state = dismissState,
                        modifier = Modifier
                            .padding(vertical = Dp(1f)),
                        directions = setOf(
                            DismissDirection.EndToStart
                        ),
                        background = {

                            val alignment = Alignment.CenterEnd
                            val icon = Icons.Default.Delete

                            val scale by animateFloatAsState(
                                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                            )

                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = Dp(20f)),
                                contentAlignment = alignment
                            ) {
                                Icon(
                                    icon,
                                    contentDescription = "Delete Icon",
                                    modifier = Modifier.scale(scale)
                                )
                            }
                        },
                        dismissContent = {

                            MusicItem(
                                music = music,
                                imageVector = Icons.Default.MusicNote,

                                onClick = {
                                    showPlayer(true)
                                    musicViewModel.addMediaItems(musics)
                                    musicViewModel.onEvent(MusicEvent.OnMusicSelected(music))
                                    musicViewModel.onEvent(MusicEvent.PlayMusic)
                                },
                            )
                        }
                    ) else MusicItem(
                    music = music,
                    imageVector = Icons.Default.MusicNote,

                    onClick = {
                        showPlayer(true)
                        musicViewModel.addMediaItems(musics)
                        musicViewModel.onEvent(MusicEvent.OnMusicSelected(music))
                        musicViewModel.onEvent(MusicEvent.PlayMusic)
                    },
                )
                Divider(Modifier.fillMaxWidth(), 2.dp, androidx.compose.ui.graphics.Color.DarkGray)
            }

        }
}


@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun MusicTracksScreenPreview() {
//    MusicTracksScreen()
}
