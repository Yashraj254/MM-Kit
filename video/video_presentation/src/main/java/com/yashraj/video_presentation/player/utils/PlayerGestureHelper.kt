package com.yashraj.video_presentation.player.utils

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.yashraj.video_presentation.player.VideoPlayerActivity
import kotlin.math.abs

@UnstableApi
@SuppressLint("ClickableViewAccessibility")
class PlayerGestureHelper(
    private val activity: VideoPlayerActivity,
    private val volumeManager: VolumeManager,
    private val brightnessManager: BrightnessManager
) {

    private val playerView: PlayerView
        get() = activity.binding.playerView

    private var pointerCount = 1
    private var isPlayingOnSeekStart: Boolean = false

    private val tapGestureDetector = GestureDetector(
        playerView.context,
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
                with(playerView) {
                    if (!isControllerFullyVisible) showController() else hideController()
                }
                return true
            }

        }
    )

    private val volumeAndBrightnessGestureDetector = GestureDetector(
        playerView.context,
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(
                firstEvent: MotionEvent?,
                currentEvent: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                if (firstEvent == null) return false

                if (activity.isControlsLocked) return false
                if (abs(distanceY / distanceX) < 2) return false


                val viewCenterX = playerView.measuredWidth / 2
                val distanceFull = playerView.measuredHeight * FULL_SWIPE_RANGE_SCREEN_RATIO
                val ratioChange = distanceY / distanceFull

                if (firstEvent.x.toInt() > viewCenterX) {
                    val change = ratioChange * volumeManager.maxStreamVolume
                    volumeManager.setVolume(volumeManager.currentVolume + change, true)
                    activity.showVolumeGestureLayout()
                } else {
                    val change = ratioChange * brightnessManager.maxBrightness
                    brightnessManager.setBrightness(brightnessManager.currentBrightness + change)
                    activity.showBrightnessGestureLayout()
                }
                return true
            }
        }
    )


    private fun releaseGestures() {
        // hide the volume indicator
        activity.hideVolumeGestureLayout()
        // hide the brightness indicator
        activity.hideBrightnessGestureLayout()


        playerView.controllerAutoShow = true
        if (isPlayingOnSeekStart) playerView.player?.play()
        isPlayingOnSeekStart = false
    }

    /**
     * Check if [firstEvent] is in the gesture exclusion area
     */

    init {
        playerView.setOnTouchListener { _, motionEvent ->
            pointerCount = motionEvent.pointerCount
            if (motionEvent.pointerCount == 1) {
                tapGestureDetector.onTouchEvent(motionEvent)
                volumeAndBrightnessGestureDetector.onTouchEvent(motionEvent)
            }

            if (motionEvent.action == MotionEvent.ACTION_UP || motionEvent.pointerCount >= 3) {
                releaseGestures()
            }
            true
        }
    }

    companion object {
        const val FULL_SWIPE_RANGE_SCREEN_RATIO = 0.66f
    }
}


enum class GestureAction {
    SWIPE
}
