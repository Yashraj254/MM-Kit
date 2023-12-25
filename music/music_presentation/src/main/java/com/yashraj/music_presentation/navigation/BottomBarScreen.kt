package com.yashraj.music_presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.material.icons.outlined.Audiotrack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.PlaylistPlay
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    data object Tracks : BottomBarScreen(
        route = "tracks",
        title = "Tracks",
        selectedIcon = Icons.Filled.Audiotrack,
        unselectedIcon = Icons.Outlined.Audiotrack
    )
    data object Folders : BottomBarScreen(
        route = "folders",
        title = "Folders",
        selectedIcon = Icons.Filled.Folder,
        unselectedIcon = Icons.Outlined.Folder
    )
    data object Playlists : BottomBarScreen(
        route = "playlists",
        title = "Playlists",
        selectedIcon = Icons.Filled.PlaylistPlay,
        unselectedIcon = Icons.Outlined.PlaylistPlay
    )
    data object Favorites : BottomBarScreen(
        route = "favorites",
        title = "Favorites",
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.Favorite
    )

}
sealed class Screen(val route: String) {
    data object MusicPlayer : Screen("music_player")
    data object FolderTracksScreen : Screen("folder_tracks")
    data object PlaylistTracksScreen : Screen("playlist_tracks")
}
