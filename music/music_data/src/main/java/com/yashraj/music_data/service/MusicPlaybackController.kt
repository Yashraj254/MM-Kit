package com.yashraj.music_data.service

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.yashraj.music_data.R
import com.yashraj.music_data.toMusic
import com.yashraj.music_domain.PlayerState
import com.yashraj.music_domain.models.Music
import com.yashraj.music_domain.service.PlaybackController
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MusicPlaybackController @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mediaControllerFuture: ListenableFuture<MediaController>,
) : PlaybackController {

    private val mediaController: MediaController?
        get() = if (mediaControllerFuture.isDone) mediaControllerFuture.get() else null

    override var mediaControllerCallback: (
        (
        playerState: PlayerState,
        currentMusic: Music?,
        currentPosition: Long,
        totalDuration: Long,
        isShuffleEnabled: Boolean,
        isRepeatOneEnabled: Boolean
    ) -> Unit
    )? = null

    init {
        Log.d("TAG", "Session token: ")

        mediaControllerFuture.addListener({
            controllerListener()
        }, MoreExecutors.directExecutor())
    }

    private fun controllerListener() {

        mediaController?.addListener(object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                updateMediaControllerCallback(player)
            }
        })
    }

    private fun updateMediaControllerCallback(player: Player) {
        with(player) {
            mediaControllerCallback?.invoke(
                playbackState.toPlayerState(isPlaying),
                currentMediaItem?.toMusic(),
                currentPosition.coerceAtLeast(0L),
                duration.coerceAtLeast(0L),
                shuffleModeEnabled,
                repeatMode == Player.REPEAT_MODE_ONE
            )
        }
    }

    override fun getMediaControllerDataOnDemand() {
        mediaController?.let {
            updateMediaControllerCallback(it)
        }
    }

    private fun Int.toPlayerState(isPlaying: Boolean) =
        when (this) {
            Player.STATE_IDLE -> PlayerState.STOPPED
            Player.STATE_ENDED -> PlayerState.STOPPED
            else -> if (isPlaying) PlayerState.PLAYING else PlayerState.PAUSED
        }

    override fun addMediaItems(musics: List<Music>) {
        val mediaItems = musics.map {
//            Log.d("TAG", "${it.imageUri}\n${Uri.parse(it.imageUri)}")
           val uri = if(it.imageUri.isBlank())
               Uri.parse(
                   ContentResolver.SCHEME_ANDROID_RESOURCE
                       + "://" + context.resources.getResourcePackageName(R.drawable.music_logo)
                       + '/' + context.resources.getResourceTypeName(R.drawable.music_logo)
                       + '/' + context.resources.getResourceEntryName(R.drawable.music_logo) )
           else Uri.parse(it.imageUri)
            Log.d("TAG", "$uri")

            MediaItem.Builder()
                .setMediaId(it.path)
                .setUri(it.path)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(it.title)
                        .setArtist(it.artist)
                        .setArtworkUri(uri)
                        .build()
                )
                .build()
        }

        mediaController?.setMediaItems(mediaItems)
    }

    override fun play(mediaItemIndex: Int) {
        mediaController?.apply {
            seekToDefaultPosition(mediaItemIndex)
            playWhenReady = true
            prepare()
        }
    }


    override fun resume() {
        mediaController?.play()
    }

    override fun pause() {
        mediaController?.pause()
    }

    override fun seekTo(position: Long) {
        mediaController?.seekTo(position)
    }

    override fun skipNext() {
        mediaController?.seekToNext()
    }

    override fun skipPrevious() {
        mediaController?.seekToPrevious()
    }

    override fun setShuffleModeEnabled(isEnabled: Boolean) {
        mediaController?.shuffleModeEnabled = isEnabled
    }

    override fun setRepeatOneEnabled(isEnabled: Boolean) {
        mediaController?.repeatMode = if (isEnabled) {
            Player.REPEAT_MODE_ONE
        } else {
            Player.REPEAT_MODE_OFF
        }
    }

    override fun getCurrentPosition() = mediaController?.currentPosition ?: 0L

    override fun getPlaybackState(): PlayerState? =
        mediaController?.playbackState?.toPlayerState(mediaController?.isPlaying ?: false)

    override fun getCurrentMusic(): Music? {
        return mediaController?.currentMediaItem?.toMusic()
    }

    override fun destroy() {
        MediaController.releaseFuture(mediaControllerFuture)
        mediaControllerCallback = null
    }
}