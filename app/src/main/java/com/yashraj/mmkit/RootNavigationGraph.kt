package com.yashraj.mmkit

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yashraj.music_presentation.navigation.MusicScreens
import com.yashraj.video_presentation.navigation.VideoScreens

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = "media"
    ){
        composable(route = "media"){
            MediaScreen(navController = navController)
        }
        composable(route = Graph.MUSIC){
            MusicScreens()
        }
        composable(route = Graph.VIDEOS){
            VideoScreens()
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val MEDIA = "media_graph"
    const val MUSIC = "music_graph"
    const val VIDEOS = "videos_graph"
}