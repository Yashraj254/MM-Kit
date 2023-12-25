package com.yashraj.music_navigations

import android.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.yashraj.music_presentation.tracks.SharedViewModel

@Composable
fun MusicScreens(sharedViewModel: SharedViewModel= hiltViewModel()) {
    val navController = rememberNavController()

    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }
    val screens = listOf(
        BottomBarScreen.Tracks,
        BottomBarScreen.Folders,
        BottomBarScreen.Playlists,
        BottomBarScreen.Favorites
    )
    Scaffold(bottomBar = {
        NavigationBar {
            screens.forEachIndexed { index, navigationItem ->
                val selected = index == navigationSelectedItem
                val icon = if (selected) {
                    navigationItem.selectedIcon
                } else {
                    navigationItem.unselectedIcon
                }
                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        navigationSelectedItem = index
                        navController.navigate(navigationItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(imageVector = icon, contentDescription = "null") },
                    label = { Text(navigationItem.title) }
                )
            }
        }
    }) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            BottomNavGraph(navController = navController,sharedViewModel = sharedViewModel)
        }
    }
}

@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun MusicScreensPreview() {
//    MusicScreens()
}
