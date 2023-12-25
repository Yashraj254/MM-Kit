package com.yashraj.video_presentation.player

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.common.VideoSize
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.media3.ui.TimeBar
import com.google.android.material.button.MaterialButton
import com.google.android.material.color.DynamicColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yashraj.utils.extensions.getFilenameFromUri
import com.yashraj.video_domain.models.Video
import com.yashraj.video_presentation.R
import com.yashraj.video_presentation.databinding.ActivityVideoPlayerBinding
import com.yashraj.video_presentation.extensions.toggleSystemBars
import com.yashraj.video_presentation.player.utils.BrightnessManager
import com.yashraj.video_presentation.player.utils.PlayerGestureHelper
import com.yashraj.video_presentation.player.utils.VolumeManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@SuppressLint("UnsafeOptInUsageError")
@AndroidEntryPoint
class VideoPlayerActivity : AppCompatActivity() {

    lateinit var binding: ActivityVideoPlayerBinding
    private lateinit var volumeManager: VolumeManager
    private lateinit var brightnessManager: BrightnessManager
    private lateinit var playerGestureHelper: PlayerGestureHelper
    private var startPosition = 0L
    private var playWhenReady = true
    private var isPlaybackFinished = false

    var isFileLoaded = false
    var isControlsLocked = false
    private var isFirstFrameRendered = false
    private var isFrameRendered = false

    private var currentOrientation: Int? = null
    var currentVideoSize: VideoSize? = null

    private val viewModel: VideoPlayerViewModel by viewModels()
    private var currentPlaylistVideos: List<Video> = emptyList()

    /**
     * Player
     */
    private lateinit var player: Player

    private var mediaSession: MediaSession? = null

    /**
     * Listeners
     */
    private val playbackStateListener: Player.Listener = playbackStateListener()

    private var hideVolumeIndicatorJob: Job? = null
    private var hideBrightnessIndicatorJob: Job? = null

    /**
     * Player controller views
     */
    private lateinit var backButton: ImageButton
    private lateinit var exoContentFrameLayout: AspectRatioFrameLayout
    private lateinit var lockControlsButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var playerLockControls: FrameLayout
    private lateinit var playerUnlockControls: FrameLayout
    private lateinit var playerCenterControls: LinearLayout
    private lateinit var prevButton: ImageButton
    private lateinit var screenRotationButton: ImageButton
    private lateinit var seekBar: TimeBar
    private lateinit var unlockControlsButton: MaterialButton
    private lateinit var videoTitleTextView: TextView
    private var gestureDetector: GestureDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Log.d("TAG", "onCreate: ${intent.data}")

        // The window is always allowed to extend into the DisplayCutout areas on the short edges of the screen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startPosition =
            savedInstanceState?.getLong("position", 0L) ?: 0L
        // Initializing views
        backButton = binding.playerView.findViewById(R.id.back_button)
        exoContentFrameLayout = binding.playerView.findViewById(R.id.exo_content_frame)
        lockControlsButton = binding.playerView.findViewById(R.id.btn_lock_controls)
        nextButton = binding.playerView.findViewById(R.id.btn_play_next)
        playerLockControls = binding.playerView.findViewById(R.id.player_lock_controls)
        playerUnlockControls = binding.playerView.findViewById(R.id.player_unlock_controls)
        playerCenterControls = binding.playerView.findViewById(R.id.player_center_controls)
        prevButton = binding.playerView.findViewById(R.id.btn_play_prev)
        screenRotationButton = binding.playerView.findViewById(R.id.btn_screen_rotation)
        seekBar = binding.playerView.findViewById(R.id.exo_progress)
        unlockControlsButton = binding.playerView.findViewById(R.id.btn_unlock_controls)
        videoTitleTextView = binding.playerView.findViewById(R.id.video_name)

        volumeManager =
            VolumeManager(audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager)
        brightnessManager = BrightnessManager(activity = this)
        playerGestureHelper = PlayerGestureHelper(
            activity = this,
            volumeManager = volumeManager,
            brightnessManager = brightnessManager
        )

    }

    @UnstableApi
    override fun onStart() {
        createPlayer()
        initializePlayerView()
        val fetchAll = intent.getBooleanExtra("fetchAll", false)
        intent.getStringExtra("folder")?.let {
            if (!fetchAll)
                viewModel.getVideosByPath(it)
            else {
                viewModel.getAllVideos()
            }
        }
        val currentMediaUri = intent.data ?: intent.getStringExtra("uri")
        lifecycleScope.launch {
            if (intent.data != null) {
                val mediaMetadata = MediaMetadata.Builder()
                    .setTitle(getFilenameFromUri(Uri.parse(currentMediaUri.toString())))
                    .build()

                val currentMediaItem =
                    createMediaStream(Uri.parse(currentMediaUri.toString())).buildUpon()
                        .setMediaMetadata(mediaMetadata).build()
                player.setMediaItem(currentMediaItem, 0L)
                playVideo()
            } else
                viewModel.videoState.collect {
                    currentPlaylistVideos = it

                    val mediaItems = it.map {
                        val mediaMetadata = MediaMetadata.Builder()
                            .setTitle(getFilenameFromUri(Uri.parse(it.imageUri)))
                            .build()
                        createMediaStream(Uri.parse(it.imageUri)).buildUpon()
                            .setMediaMetadata(mediaMetadata).build()
                    }
                    val mediaMetadata = MediaMetadata.Builder()
                        .setTitle(getFilenameFromUri(Uri.parse(currentMediaUri.toString())))
                        .build()

                    val currentMediaItem =
                        createMediaStream(Uri.parse(currentMediaUri.toString())).buildUpon()
                            .setMediaMetadata(mediaMetadata).build()
                    player.setMediaItems(mediaItems, mediaItems.indexOf(currentMediaItem), 0L)
                    if (mediaItems.isNotEmpty())
                        playVideo()
                }

        }

        super.onStart()
    }

    override fun onStop() {
        currentOrientation = requestedOrientation
        releasePlayer()
        super.onStop()
    }

    private fun createPlayer() {
        Timber.d("Creating player")

        player = ExoPlayer.Builder(applicationContext)
            .setAudioAttributes(getAudioAttributes(), true)
            .setHandleAudioBecomingNoisy(true)
            .build()

        try {
            if (player.canAdvertiseSession()) {
                mediaSession = MediaSession.Builder(this, player).build()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        player.addListener(playbackStateListener)
    }

    @UnstableApi
    private fun initializePlayerView() {

        binding.playerView.apply {
            setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
            player = this@VideoPlayerActivity.player
            setControllerVisibilityListener(
                PlayerView.ControllerVisibilityListener { visibility ->
                    toggleSystemBars(showBars = visibility == View.VISIBLE && !isControlsLocked)
                }
            )
        }

        nextButton.setOnClickListener {
            if (player.hasNextMediaItem()) {
                player.seekToNextMediaItem()
                videoTitleTextView.text = player.currentMediaItem?.mediaMetadata?.title

            }
        }
        prevButton.setOnClickListener {
            if (player.hasPreviousMediaItem()) {
                player.seekToPreviousMediaItem()
                videoTitleTextView.text = player.currentMediaItem?.mediaMetadata?.title

            }
        }
        lockControlsButton.setOnClickListener {
            playerUnlockControls.visibility = View.INVISIBLE
            playerLockControls.visibility = View.VISIBLE
            isControlsLocked = true
            toggleSystemBars(showBars = false)
        }
        unlockControlsButton.setOnClickListener {
            playerLockControls.visibility = View.INVISIBLE
            playerUnlockControls.visibility = View.VISIBLE
            isControlsLocked = false
            binding.playerView.showController()
            toggleSystemBars(showBars = true)
        }

        screenRotationButton.setOnClickListener {
            requestedOrientation = when (resources.configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                else -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            }
        }

        backButton.setOnClickListener { finish() }
    }


    private fun playVideo() {
        lifecycleScope.launch {
            val uri = intent.data ?: intent.getStringExtra("uri")
            val mediaUri = Uri.parse(uri.toString())
            withContext(Dispatchers.Main) {
                videoTitleTextView.text = mediaUri?.let { getFilenameFromUri(it) }

                player.playWhenReady = playWhenReady
                player.seekTo(startPosition)
                player.prepare()
                player.play()
            }
        }
    }

    private fun releasePlayer() {
        Timber.d("Releasing player")
        playWhenReady = player.playWhenReady
        player.removeListener(playbackStateListener)
        player.release()
        mediaSession?.release()
        mediaSession = null
    }

    private fun playbackStateListener() = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            binding.playerView.keepScreenOn = isPlaying
            super.onIsPlayingChanged(isPlaying)
        }

        @SuppressLint("SourceLockedOrientationActivity")
        override fun onVideoSizeChanged(videoSize: VideoSize) {
            currentVideoSize = videoSize
            if (currentOrientation != null) return
            super.onVideoSizeChanged(videoSize)
        }

        override fun onPlayerError(error: PlaybackException) {
            Timber.e(error)
            val alertDialog = MaterialAlertDialogBuilder(this@VideoPlayerActivity)
                .setTitle("Error playing video")
                .setMessage(error.message ?: "Error playing video")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .setOnDismissListener {
                    finish()
                }
                .create()

            alertDialog.show()
            super.onPlayerError(error)
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                Player.STATE_ENDED -> {
                    Timber.d("Player state: ENDED")
                    isPlaybackFinished = true
//                    finish()
                }

                Player.STATE_READY -> {
                    Timber.d("Player state: READY")
                    isFrameRendered = true
                    isFileLoaded = true
                }

                Player.STATE_BUFFERING -> {
                    Timber.d("Player state: BUFFERING")
                }

                Player.STATE_IDLE -> {
                    Timber.d("Player state: IDLE")
                }
            }
            super.onPlaybackStateChanged(playbackState)
        }

        @UnstableApi
        override fun onRenderedFirstFrame() {
            isFirstFrameRendered = true
            binding.playerView.setShowBuffering(PlayerView.SHOW_BUFFERING_NEVER)
            super.onRenderedFirstFrame()
        }

        override fun onTracksChanged(tracks: Tracks) {
            super.onTracksChanged(tracks)
            if (isFirstFrameRendered) return
        }
    }


    override fun setRequestedOrientation(requestedOrientation: Int) {
        super.setRequestedOrientation(requestedOrientation)
//        screenRotationButton.setImageDrawable(this, getRotationDrawable())
    }

    private fun getAudioAttributes(): AudioAttributes {
        return AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
            .build()
    }

    private fun createMediaStream(uri: Uri) = MediaItem.Builder()
        .setMediaId(uri.toString())
        .setUri(uri)
        .build()

    fun hideVolumeGestureLayout() {
        if (binding.volumeGestureLayout.visibility != View.VISIBLE) return
        hideVolumeIndicatorJob = lifecycleScope.launch {
            binding.volumeGestureLayout.visibility = View.GONE
        }
    }

    fun hideBrightnessGestureLayout() {
        if (binding.brightnessGestureLayout.visibility != View.VISIBLE) return
        hideBrightnessIndicatorJob = lifecycleScope.launch {
            binding.brightnessGestureLayout.visibility = View.GONE
        }
    }

    fun showVolumeGestureLayout() {
        hideVolumeIndicatorJob?.cancel()
        with(binding) {
            volumeGestureLayout.visibility = View.VISIBLE
            volumeProgressBar.max = volumeManager.maxVolume.times(100)
            volumeProgressBar.progress = volumeManager.currentVolume.times(100).toInt()
            volumeProgressText.text = volumeManager.volumePercentage.toString()
        }
    }

    fun showBrightnessGestureLayout() {
        hideBrightnessIndicatorJob?.cancel()
        with(binding) {
            brightnessGestureLayout.visibility = View.VISIBLE
            brightnessProgressBar.max = brightnessManager.maxBrightness.times(100).toInt()
            brightnessProgressBar.progress = brightnessManager.currentBrightness.times(100).toInt()
            brightnessProgressText.text = brightnessManager.brightnessPercentage.toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("position", player.currentPosition)
    }
}