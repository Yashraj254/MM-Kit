package com.yashraj.ui

import androidx.annotation.RawRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieLoaderAnimation(
    @RawRes lottieRes: Int,
    title: String,
    modifier: Modifier,
) {
    val mediaComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(lottieRes))
    val progressAnimation by animateLottieCompositionAsState(
        composition = mediaComposition,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LottieAnimation(
            composition = mediaComposition,
            progress = { progressAnimation },
            modifier = modifier
        )
        if (title.isNotBlank())
            Text(text = title, fontWeight = FontWeight.Bold)
    }

}