package com.yashraj.music_presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
    var title by remember { mutableStateOf("MM Kit") }
    var folderTracksTitle by remember { mutableStateOf("MM Kit") }
    val routes = listOf(
        BottomBarScreen.Tracks.route,
        BottomBarScreen.Folders.route,
        BottomBarScreen.Playlists.route,
        BottomBarScreen.Favorites.route,
        Screen.PlaylistTracksScreen.route,
        Screen.FolderTracksScreen.route
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    routes.forEachIndexed { _, _ ->
        title = when(currentRoute){
            Screen.PlaylistTracksScreen.route -> {
                folderTracksTitle
            }
            Screen.FolderTracksScreen.route -> {
                folderTracksTitle
            }
            BottomBarScreen.Tracks.route -> {
                "Tracks"
            }
            BottomBarScreen.Folders.route -> {
                "Folders"
            }
            BottomBarScreen.Playlists.route -> {
                "Playlists"
            }
            BottomBarScreen.Favorites.route -> {
                "Favorites"
            }
            else->{
                "MM Kit"
            }
        }
//         if (currentRoute == Screen.PlaylistTracksScreen.route) {
//            title="Tracks"
//        }
//        if (currentRoute == Screen.FolderTracksScreen.route) {
//             title="ABC"
//        }
    }
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = title) }) },
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
                        isPlaylistTracks = false,
                        isFavoriteTracks = false,
                        musicState = musicState
                    )
                }
                composable(route = BottomBarScreen.Folders.route) {
                    MusicFoldersScreen(
                        folderName = {
                            folderTracksTitle = it
                        },
                        navigateToFolderTracksScreen = {
                            directoriesViewModel.getFolderTracks(folderPath = it)
                            navController.navigate(Screen.FolderTracksScreen.route)
                        }
                    )
                }
                composable(route = BottomBarScreen.Playlists.route) {
                    MusicPlaylistsScreen(
                        playlistName = {
                            folderTracksTitle = it
                        },
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
                        musicUiState = playlistViewModel.musicState.collectAsState().value,
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
