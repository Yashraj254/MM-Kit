package com.yashraj.video_presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yashraj.video_presentation.folders.FolderTracksScreen
import com.yashraj.video_presentation.folders.VideoDirectoriesViewModel
import com.yashraj.video_presentation.folders.VideoFoldersScreen
import com.yashraj.video_presentation.tracks.VideoTracksScreen
import com.yashraj.video_presentation.tracks.VideoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavGraph(
    navController: NavHostController,
    directoriesViewModel: VideoDirectoriesViewModel = hiltViewModel(),
    videosViewModel: VideoViewModel = hiltViewModel()
) {
    val videos = videosViewModel.videoState.collectAsStateWithLifecycle().value
    val folderVideos = directoriesViewModel.videosState.collectAsState().value
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "MM Kit") }) },
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(
                navController = navController,
                startDestination = BottomBarScreen.Videos.route
            ) {
                composable(route = BottomBarScreen.Videos.route) {
                    VideoTracksScreen(
                        videos,
                        fetchAllVideos = true
                    )
                }
                composable(route = BottomBarScreen.Folders.route) {
                    VideoFoldersScreen(
                        navigateToFolderTracksScreen = {
                            directoriesViewModel.getFolderTracks(folderPath = it)
                            navController.navigate(Screen.FolderTracksScreen.route)
                        }
                    )
                }

                composable(route = Screen.FolderTracksScreen.route) {
                    FolderTracksScreen(folderVideos)
                }
            }
        }
    }
}
