package com.yashraj.music_presentation.player

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.yashraj.music_domain.PlayerState
import com.yashraj.music_presentation.player.extension.noRippleClickable
import com.yashraj.music_presentation.tracks.SharedViewModel
import com.yashraj.ui.LottieLoaderAnimation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MusicMiniPlayerCard(
    onResumeClicked: () -> Unit,
    onPauseClicked: () -> Unit,
    currentFraction: Float,
    isCollapsed: Boolean,
    onSheetClick: () -> Unit,
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val playerState = sharedViewModel.musicPlaybackUiState.playerState
    val music = sharedViewModel.currentMusic()

    Card(
        modifier = Modifier
            .offset(y = Dp(currentFraction) * -(80).dp.value)
            .noRippleClickable(
                enabled = isCollapsed,
                onClick = onSheetClick
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.weight(1f)) {
                music?.run {
                    if (!imageUri.startsWith("android.resource"))
                        GlideImage(
                            modifier = Modifier
                                .size(45.dp)
                                .clip(MaterialTheme.shapes.small), imageModel = {
                                imageUri
                            },
                            imageOptions = ImageOptions(
                                contentScale = ContentScale.FillBounds,
                                alignment = Alignment.Center
                            )
                        ) else
                        LottieLoaderAnimation(
                            lottieRes = com.yashraj.ui.R.raw.animation_music,
                            title = "",
                            modifier = Modifier
                                .size(45.dp)
                                .clip(MaterialTheme.shapes.large)
                        )
                    Spacer(modifier = Modifier.width(15.dp))
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = artist,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            IconButton(
                onClick = {
                    when (playerState) {
                        PlayerState.PLAYING -> onPauseClicked()
                        PlayerState.PAUSED -> onResumeClicked()
                        else -> {}
                    }
                }
            ) {
                Icon(
                    imageVector = if (playerState == PlayerState.PLAYING) {
                        Icons.Rounded.Pause
                    } else {
                        Icons.Rounded.PlayArrow
                    },
                    contentDescription = "Play or pause button"
                )
            }
        }
    }
}