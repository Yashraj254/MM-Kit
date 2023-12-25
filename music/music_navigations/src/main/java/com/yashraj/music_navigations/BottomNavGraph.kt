package com.yashraj.music_navigations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yashraj.music_presentation.components.MusicMiniPlayerCard
import com.yashraj.music_presentation.favorites.MusicFavoritesScreen
import com.yashraj.music_presentation.folders.FolderTracksScreen
import com.yashraj.music_presentation.folders.MusicDirectoriesViewModel
import com.yashraj.music_presentation.folders.MusicFoldersScreen
import com.yashraj.music_presentation.player.MusicPlayerScreen
import com.yashraj.music_presentation.player.MusicPlayerViewModel
import com.yashraj.music_presentation.playlists.MusicPlaylistsScreen
import com.yashraj.music_presentation.tracks.MusicTracksScreen
import com.yashraj.music_presentation.tracks.MusicViewModel
import com.yashraj.music_presentation.tracks.SharedViewModel

@Composable
fun BottomNavGraph(navController: NavHostController,sharedViewModel: SharedViewModel) {
    val musicPlaybackUiState = sharedViewModel.musicPlaybackUiState
    val musicViewModel: MusicViewModel = hiltViewModel()
    val directoriesViewModel: MusicDirectoriesViewModel = hiltViewModel()
    val musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel()
    val musics = musicViewModel.musicState.collectAsState().value.musics
    if(!musics.isNullOrEmpty()){
        LaunchedEffect(key1 = true){
            musicViewModel.addMediaItems(musics)
        }
    }
    Box(modifier = Modifier.fillMaxSize()){
        NavHost(navController = navController, startDestination = BottomBarScreen.Tracks.route) {
            composable(route = BottomBarScreen.Tracks.route) {

                MusicTracksScreen(
                    onEvent = musicViewModel::onEvent,
                    musicUiState = musicViewModel.musicState.collectAsState().value,
                    onNavigateToMusicPlayer = {
                        navController.navigate(Screen.MusicPlayer.route)
                    },
                    musicPlaybackUiState = musicPlaybackUiState
                )
            }
            composable(route = BottomBarScreen.Folders.route) {
                MusicFoldersScreen(
                    navigateToFolderTracksScreen = {
                        directoriesViewModel.getFolderTracks(folderPath = it)
                        navController.navigate(Screen.FolderTracksScreen.route)
                    }
                )
            }
            composable(route = BottomBarScreen.Playlists.route) {
                MusicPlaylistsScreen()
            }
            composable(route = BottomBarScreen.Favorites.route) {
                MusicFavoritesScreen(
                    onEvent = musicViewModel::onEvent,
                    musicUiState = musicViewModel.favoriteMusicState.collectAsState().value,
                    onNavigateToMusicPlayer = {
                        navController.navigate(Screen.MusicPlayer.route)
                    },
                    musicPlaybackUiState = musicPlaybackUiState
                )
            }
            composable(route = Screen.FolderTracksScreen.route){
                FolderTracksScreen(
                    onEvent = musicViewModel::onEvent,
                    musicUiState = directoriesViewModel.musicState.collectAsState().value,
                    onNavigateToMusicPlayer = {
                        navController.navigate(Screen.MusicPlayer.route)
                    },
                    musicPlaybackUiState = musicPlaybackUiState
                )
            }
            composable(route = Screen.MusicPlayer.route) {

                MusicPlayerScreen(
                    musicPlaybackUiState = musicPlaybackUiState,
                    navController = navController
                )
            }
        }

        if(currentMusic!=null) {
            android.util.Log.d("TAG", "BottomNavGraph: ")
            MusicMiniPlayerCard(
                music = musicPlaybackUiState.currentMusic,
                playerState = musicPlaybackUiState.playerState,
                onResumeClicked = { /*TODO*/ },
                onPauseClicked = { /*TODO*/ },
                onClick = { }
            )
        }
    }
}
