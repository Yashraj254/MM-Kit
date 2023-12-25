package com.example.music_presentation.screens.playlists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.yashraj.music_domain.models.Playlist
import com.yashraj.music_presentation.R
import com.yashraj.music_presentation.playlists.InputDialogView
import com.yashraj.music_presentation.playlists.MusicPlaylistViewModel


@Composable
fun AddToPlaylistDialog(
    viewModel: MusicPlaylistViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    playlist: (Playlist) -> Unit,
) {
    val state = viewModel.playlistState.collectAsState()
    var listItems by remember {
        mutableStateOf(emptyList<Playlist>())
    }
    listItems = state.value.playlists
    var playlistTitle by remember {
        mutableStateOf("")
    }
    var showInputDialog by remember {
        mutableStateOf(false)
    }
    if (showInputDialog) {
        InputDialogView(
            onDismiss = { showInputDialog = false },
            title = {
                viewModel.createNewPlaylist(it)
                showInputDialog = false
            })
    }
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
//            shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .size(320.dp, 360.dp)
                .padding(8.dp),
            elevation = 8.dp
        ) {
            Column(
                Modifier
                    .background(Color.White)
            ) {
                Text(
                    text = "Add to playlist",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    item {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { showInputDialog = true }) {
                            Icon(
                                imageVector = Icons.Filled.Folder,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondaryContainer,
                                modifier = Modifier.size(50.dp)
                            )
                            Text(
                                text = "New Playlist",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(16.dp),
                            )
                        }
                    }
                    items(listItems.size) { i ->
                        val playlist = listItems[i]
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { playlist(playlist) }) {
                            Icon(
                                imageVector = Icons.Filled.Folder,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondaryContainer,
                                modifier = Modifier.size(50.dp)
                            )
                            Text(
                                text = playlist.playlist,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(16.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}