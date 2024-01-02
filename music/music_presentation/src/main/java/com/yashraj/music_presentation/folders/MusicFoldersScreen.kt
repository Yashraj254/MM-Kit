package com.yashraj.music_presentation.folders

import android.graphics.Color
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yashraj.music_presentation.components.FolderItem
import com.yashraj.music_presentation.components.MusicItem

@Composable
fun MusicFoldersScreen(
    viewModel: MusicDirectoriesViewModel = hiltViewModel(),
    folderName:(String) -> Unit,
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
                        folderName(folder.name)
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
