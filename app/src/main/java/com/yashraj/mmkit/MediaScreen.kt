package com.yashraj.mmkit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yashraj.music_presentation.tracks.SharedViewModel
import com.yashraj.ui.LottieLoaderAnimation

@Composable
fun MediaScreen(navController: NavController, viewModel: SharedViewModel = hiltViewModel()) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        LottieLoaderAnimation(
            lottieRes = com.yashraj.ui.R.raw.animation_music,
            title = "Music",
            modifier = Modifier
                .size(150.dp)
                .clickable {  navController.navigate(Graph.MUSIC)}
        )
        LottieLoaderAnimation(
            lottieRes = com.yashraj.ui.R.raw.animation_videos,
            title = "Videos",
            modifier = Modifier
                .size(150.dp)
                .clickable {  navController.navigate(Graph.VIDEOS)}
        )
    }
}

