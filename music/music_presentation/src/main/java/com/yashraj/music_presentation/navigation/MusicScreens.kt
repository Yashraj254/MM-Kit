package com.yashraj.music_presentation.navigation

import android.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yashraj.music_presentation.player.MusicMiniPlayerCard
import com.yashraj.music_presentation.components.SheetContent
import com.yashraj.music_presentation.player.MusicPlayerEvent
import com.yashraj.music_presentation.player.MusicPlayerScreen
import com.yashraj.music_presentation.player.MusicPlayerViewModel
import com.yashraj.music_presentation.player.extension.currentFraction
import com.yashraj.music_presentation.tracks.SharedViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MusicScreens(
    sharedViewModel: SharedViewModel = hiltViewModel(),
    musicPlayerViewModel: MusicPlayerViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    var sheetHeight by remember { mutableStateOf(0.dp) }

    var isBottomSheetVisible by remember { mutableStateOf(sharedViewModel.currentMusic() != null) }
    if (isBottomSheetVisible) sheetHeight = 70.dp

    val scope = rememberCoroutineScope()
    val scaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed))
    val sheetToggle: () -> Unit = {
        scope.launch {
            if (scaffoldState.bottomSheetState.isCollapsed) {
                scaffoldState.bottomSheetState.expand()
            } else {
                scaffoldState.bottomSheetState.collapse()
            }
        }
    }
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }
    val screens = listOf(
        BottomBarScreen.Tracks,
        BottomBarScreen.Folders,
        BottomBarScreen.Playlists,
        BottomBarScreen.Favorites
    )
    val shouldShowBottomNavigation = when (currentRoute(navController)) {
        BottomBarScreen.Tracks.route,
        BottomBarScreen.Folders.route,
        BottomBarScreen.Playlists.route,
        BottomBarScreen.Favorites.route,
        -> true

        else -> false
    }

    Scaffold(bottomBar = {
        if (shouldShowBottomNavigation)
            NavigationBar(
                tonalElevation = 0.dp,
                modifier = Modifier.offset(y = Dp(scaffoldState.currentFraction) * 80.dp.value),
                containerColor = androidx.compose.ui.graphics.Color.Transparent
            ) {
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

            BottomSheetScaffold(
                sheetContent = {
                    SheetContent {

                        Surface {

                            MusicPlayerScreen {
                                sheetToggle()
                            }
                            MusicMiniPlayerCard(
                                onResumeClicked = { musicPlayerViewModel.onEvent(MusicPlayerEvent.ResumeMusic) },
                                onPauseClicked = { musicPlayerViewModel.onEvent(MusicPlayerEvent.PauseMusic) },
                                isCollapsed = scaffoldState.bottomSheetState.isCollapsed,
                                onSheetClick = sheetToggle,
                                currentFraction = scaffoldState.currentFraction,
                            )
                        }
                    }
                },
                scaffoldState = scaffoldState,
                sheetPeekHeight = sheetHeight
            ) {

                BottomNavGraph(
                    navController = navController
                ) {
                    isBottomSheetVisible = it
                }
            }
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