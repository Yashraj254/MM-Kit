package com.yashraj.music_presentation.player

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.PauseCircle
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material.icons.rounded.PlaylistAdd
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material.icons.rounded.ShuffleOn
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yashraj.music_presentation.playlists.AddToPlaylistDialog
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.yashraj.music_domain.PlayerState
import com.yashraj.music_presentation.tracks.MusicViewModel
import com.yashraj.music_presentation.tracks.SharedViewModel
import com.yashraj.ui.LottieLoaderAnimation
import com.yashraj.utils.toTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MusicPlayerScreen(
//    musicPlayerViewModel.onEvent: (MusicPlayerEvent) -> Unit,
    sharedViewModel: SharedViewModel = hiltViewModel(),
    musicViewModel: MusicViewModel = hiltViewModel(),
    musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel(),
    onCollapse: () -> Unit
) {

    val musicPlaybackUiState = sharedViewModel.musicPlaybackUiState
    var showPlaylistDialog by remember {
        mutableStateOf(false)
    }
    if (showPlaylistDialog) {
        AddToPlaylistDialog(
            onDismiss = { showPlaylistDialog = false },
            playlist = { playlist ->
                val music = musicPlaybackUiState.currentMusic
                music?.let {
                    musicViewModel.addToPlaylist(playlist.playlistId, it)
                }
                showPlaylistDialog = false
            })
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = {
                        onCollapse()

                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "Minimize music player"
                    )
                }
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = {
                        showPlaylistDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PlaylistAdd,
                        contentDescription = "Show Playlist Dialog"
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                with(musicPlaybackUiState) {
                    currentMusic?.run {
                        if (!imageUri.startsWith("android.resource"))
                            GlideImage(
                                imageModel = { imageUri },
                                imageOptions = ImageOptions(
                                    contentScale = ContentScale.Crop,
                                    alignment = Alignment.Center
                                ),
                                modifier = Modifier
                                    .size(280.dp)
                                    .clip(MaterialTheme.shapes.large)
                            )
                        else
                            LottieLoaderAnimation(
                                lottieRes = com.yashraj.ui.R.raw.animation_music,
                                title = "",
                                modifier = Modifier
                                    .size(280.dp)
                                    .clip(MaterialTheme.shapes.large)
                            )

                        Spacer(modifier = Modifier.height(40.dp))
                        Text(
                            text = title,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = artist,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    // Music Playback
                    Spacer(modifier = Modifier.height(30.dp))
                    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                        Slider(
                            value = currentPosition.toFloat(),
                            valueRange = 0f..totalDuration.toFloat(),
                            onValueChange = {
                                musicPlayerViewModel.onEvent(MusicPlayerEvent.SeekMusicPosition(it.toLong()))
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = currentPosition.toTime(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = totalDuration.toTime(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(32.dp)
                                .clickable {
                                    if (isShuffleEnabled) {
                                        musicPlayerViewModel.onEvent(
                                            MusicPlayerEvent.SetMusicShuffleEnabled(
                                                false
                                            )
                                        )
                                    } else {
                                        musicPlayerViewModel.onEvent(
                                            MusicPlayerEvent.SetMusicShuffleEnabled(
                                                true
                                            )
                                        )
                                    }
                                },
                            imageVector = if (isShuffleEnabled) {
                                Icons.Rounded.ShuffleOn
                            } else {
                                Icons.Rounded.Shuffle
                            },
                            contentDescription = "Shuffle button"
                        )
                        Icon(
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { musicPlayerViewModel.onEvent(MusicPlayerEvent.SkipPreviousMusic) },
                            imageVector = Icons.Rounded.SkipPrevious,
                            contentDescription = "Skip previous button"
                        )
                        Icon(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .clickable {
                                    when (playerState) {
                                        PlayerState.PLAYING -> musicPlayerViewModel.onEvent(
                                            MusicPlayerEvent.PauseMusic
                                        )

                                        PlayerState.PAUSED -> musicPlayerViewModel.onEvent(
                                            MusicPlayerEvent.ResumeMusic
                                        )

                                        else -> {}
                                    }
                                },
                            imageVector = if (playerState == PlayerState.PLAYING) {
                                Icons.Rounded.PauseCircle
                            } else {
                                Icons.Rounded.PlayCircle
                            },
                            contentDescription = "Play or pause button"
                        )
                        Icon(
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { musicPlayerViewModel.onEvent(MusicPlayerEvent.SkipNextMusic) },
                            imageVector = Icons.Rounded.SkipNext,
                            contentDescription = "Skip next button"
                        )
                        var favIcon by remember {
                            mutableStateOf(Icons.Rounded.FavoriteBorder)
                        }
                        favIcon = if (currentMusic?.path?.let {
                                musicViewModel.isAddedToFavorites(
                                    it
                                )
                            } == true) {
                            Icons.Rounded.Favorite
                        } else {
                            Icons.Rounded.FavoriteBorder
                        }
                        Icon(
                            modifier = Modifier
                                .size(32.dp)
                                .clickable {
                                    if (currentMusic != null) {
                                        favIcon =
                                            if (musicViewModel.isAddedToFavorites(currentMusic.path)) {
                                                musicViewModel.removeFromFavorites(currentMusic.path)
                                                Icons.Rounded.FavoriteBorder
                                            } else {
                                                musicViewModel.addToFavorites(currentMusic.path)
                                                Icons.Rounded.Favorite
                                            }
                                    }
                                },
                            imageVector = favIcon,
                            contentDescription = "Favorite"
                        )
//                           Icon(
//                            modifier = Modifier
//                                .size(32.dp)
//                                .clickable {
//                                    if (isRepeatOneEnabled) {
//                                        musicPlayerViewModel.onEvent(MusicPlayerEvent.SetPlayerRepeatOneEnabled(false))
//                                    } else {
//                                        musicPlayerViewModel.onEvent(MusicPlayerEvent.SetPlayerRepeatOneEnabled(true))
//                                    }
//                                },
//                            imageVector = if (isRepeatOneEnabled) {
//                                Icons.Rounded.RepeatOneOn
//                            } else {
//                                Icons.Rounded.RepeatOne
//                            },
//                            contentDescription = "Repeat button"
//                        )

                    }
                }
            }
        }
    }
}