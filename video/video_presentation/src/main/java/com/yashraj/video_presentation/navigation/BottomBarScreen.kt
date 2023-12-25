package com.yashraj.video_presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.outlined.Audiotrack
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    data object Videos : BottomBarScreen(
        route = "videos",
        title = "Videos",
        selectedIcon = Icons.Filled.Audiotrack,
        unselectedIcon = Icons.Outlined.Audiotrack
    )
    data object Folders : BottomBarScreen(
        route = "folders",
        title = "Folders",
        selectedIcon = Icons.Filled.Folder,
        unselectedIcon = Icons.Outlined.Folder
    )


}
sealed class Screen(val route: String) {
    data object MusicPlayer : Screen("music_player")
    data object FolderTracksScreen : Screen("folder_tracks")
}
