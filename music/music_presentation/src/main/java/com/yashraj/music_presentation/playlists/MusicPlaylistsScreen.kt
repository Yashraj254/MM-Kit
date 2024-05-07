package com.yashraj.music_presentation.playlists

import android.graphics.Color
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yashraj.music_domain.models.Playlist
import com.yashraj.music_presentation.components.FolderItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicPlaylistsScreen(
    musicPlaylistViewModel: MusicPlaylistViewModel = hiltViewModel(),
    playlistName: (String) -> Unit,
    navigateToPlaylistTracksScreen: (Int) -> Unit
) {
    val state = musicPlaylistViewModel.playlistState.collectAsState().value
    var showInputDialog by remember {
        mutableStateOf(false)
    }
    if (showInputDialog) {
        InputDialogView(
            onDismiss = { showInputDialog = false },
            title = {
                musicPlaylistViewModel.createNewPlaylist(it)
                showInputDialog = false
            })
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showInputDialog = true
                    }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.padding(4.dp)
                )
                Text(text = "Create new playlist", modifier = Modifier.padding(4.dp))
            }
        }
        items(state.playlists, key = { item: Playlist -> item.playlistId }) { playlist ->
            val dismissState = rememberDismissState()

            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                musicPlaylistViewModel.deletePlaylist(playlist)
            }

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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        FolderItem(
                            folderName = playlist.playlist,
                            onClick = {
                                playlistName(playlist.playlist)
                                navigateToPlaylistTracksScreen(playlist.playlistId)
                            }
                        )
                    }
                })
        }
    }
}

@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun MusicPlaylistsScreenPreview() {
//    MusicPlaylistsScreen()
}
