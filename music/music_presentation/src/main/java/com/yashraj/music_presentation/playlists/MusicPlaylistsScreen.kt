package com.yashraj.music_presentation.playlists

import android.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yashraj.music_presentation.components.FolderItem

@Composable
fun MusicPlaylistsScreen(
    musicPlaylistViewModel: MusicPlaylistViewModel = hiltViewModel(),
    playlistName:(String) -> Unit,
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
                    .fillMaxWidth().
                    clickable {
                        showInputDialog = true
                    }.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null,modifier = Modifier.padding(4.dp))
                Text(text = "Create new playlist",modifier = Modifier.padding(4.dp))
            }
        }
        items(state.playlists.size) { i ->
            val playlist = state.playlists[i]
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
        }
    }
}

@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun MusicPlaylistsScreenPreview() {
//    MusicPlaylistsScreen()
}
