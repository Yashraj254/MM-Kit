package com.yashraj.video_presentation.player.utils

import android.view.WindowManager
import com.yashraj.video_presentation.extensions.currentBrightness
import com.yashraj.video_presentation.player.VideoPlayerActivity

class BrightnessManager(private val activity: VideoPlayerActivity) {

    var currentBrightness = activity.currentBrightness
    val maxBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL

    val brightnessPercentage get() = (currentBrightness / maxBrightness).times(100).toInt()

    fun setBrightness(brightness: Float) {
        currentBrightness = brightness.coerceIn(0f, maxBrightness)
        val layoutParams = activity.window.attributes
        layoutParams.screenBrightness = currentBrightness
        activity.window.attributes = layoutParams

        // fixes a bug which makes the action bar reappear after changing the brightness
    }
}
