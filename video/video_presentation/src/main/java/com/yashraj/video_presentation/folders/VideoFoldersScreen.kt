package com.yashraj.video_presentation.folders

import android.graphics.Color
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.yashraj.video_presentation.components.FolderItem

@Composable
fun VideoFoldersScreen(
    viewModel: VideoDirectoriesViewModel = hiltViewModel(),
    navigateToFolderTracksScreen: (String) -> Unit
) {
    val state = viewModel.state.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(state.folders.size) { i ->
            val folder = state.folders[i]
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                FolderItem(
                    folderName = folder.name,
                    onClick = {
                        navigateToFolderTracksScreen(folder.path)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun MusicFoldersScreenPreview() {
//    MusicFoldersScreen()
}
