package com.yashraj.video_presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    directoriesViewModel: VideoDirectoriesViewModel = hiltViewModel(),
    videosViewModel: VideoViewModel = hiltViewModel()
) {
    val videos = videosViewModel.videoState.collectAsStateWithLifecycle().value
    val folderVideos = directoriesViewModel.videosState.collectAsState().value

    Box(modifier = Modifier.fillMaxSize()) {
        Log.d("TAG", "BottomNavGraph: ")
        NavHost(navController = navController, startDestination = BottomBarScreen.Videos.route) {
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
