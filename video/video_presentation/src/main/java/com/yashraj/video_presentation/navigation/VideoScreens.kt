package com.yashraj.video_presentation.navigation

import android.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun VideoScreens(
) {
    val navController = rememberNavController()

    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }
    val screens = listOf(
        BottomBarScreen.Videos,
        BottomBarScreen.Folders,
    )
    val shouldShowBottomNavigation = when (currentRoute(navController)) {
        BottomBarScreen.Videos.route,
        BottomBarScreen.Folders.route,
        -> true

        else -> false
    }
    Scaffold(bottomBar = {
        if (shouldShowBottomNavigation)
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                screens.forEachIndexed { index, navigationItem ->
                    val selected = currentRoute == navigationItem.route
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
            BottomNavGraph(
                navController = navController
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun MusicScreensPreview() {
//    MusicScreens()
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}