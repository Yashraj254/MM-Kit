package com.yashraj.music_presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yashraj.music_presentation.favorites.MusicFavoritesScreen
import com.yashraj.music_presentation.folders.FolderTracksScreen
import com.yashraj.music_presentation.folders.MusicDirectoriesViewModel
import com.yashraj.music_presentation.folders.MusicFoldersScreen
import com.yashraj.music_presentation.playlists.MusicPlaylistViewModel
import com.yashraj.music_presentation.playlists.MusicPlaylistsScreen
import com.yashraj.music_presentation.playlists.PlaylistTracksScreen
import com.yashraj.music_presentation.tracks.MusicTracksScreen
import com.yashraj.music_presentation.tracks.MusicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavGraph(
    navController: NavHostController,
    showPlayer: (Boolean) -> Unit
) {
    val musicViewModel: MusicViewModel = hiltViewModel()
    val directoriesViewModel: MusicDirectoriesViewModel = hiltViewModel()
    val playlistViewModel: MusicPlaylistViewModel = hiltViewModel()
    val favoriteState = musicViewModel.favoriteMusicState.collectAsStateWithLifecycle().value
    val musicState = musicViewModel.musicState.collectAsStateWithLifecycle().value

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "MM Kit") }) },
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(
                navController = navController,
                startDestination = BottomBarScreen.Tracks.route
            ) {
                composable(route = BottomBarScreen.Tracks.route) {
                    MusicTracksScreen(
                        showPlayer = {
                            showPlayer(it)
                        },
                        musicState = musicState
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
                    MusicPlaylistsScreen(
                        navigateToPlaylistTracksScreen = {
                            playlistViewModel.getPlaylistTracks(it)
                            navController.navigate(Screen.PlaylistTracksScreen.route)
                        }
                    )
                }
                composable(route = Screen.PlaylistTracksScreen.route) {
                    PlaylistTracksScreen(
                        showPlayer = {
                            showPlayer(it)
                        },
                        musicUiState = directoriesViewModel.musicState.collectAsState().value,
                    )
                }
                composable(route = BottomBarScreen.Favorites.route) {
                    MusicFavoritesScreen(
                        showPlayer = {
                            showPlayer(it)
                        },
                        musicState = favoriteState
                    )
                }
                composable(route = Screen.FolderTracksScreen.route) {
                    FolderTracksScreen(
                        showPlayer = {
                            showPlayer(it)
                        },
                        musicUiState = directoriesViewModel.musicState.collectAsState().value,
                    )
                }
            }
        }


    }
}
