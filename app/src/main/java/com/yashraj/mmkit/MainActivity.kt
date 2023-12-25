package com.yashraj.mmkit

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.yashraj.mmkit.ui.theme.MMKitTheme
import com.yashraj.music_data.MusicSynchronizer
import com.yashraj.music_data.service.MusicService
import com.yashraj.music_presentation.player.MusicPlayerScreen
import com.yashraj.music_presentation.tracks.MusicEvent
import com.yashraj.music_presentation.tracks.MusicViewModel
import com.yashraj.music_presentation.tracks.SharedViewModel
import com.yashraj.utils.extensions.getPath
import com.yashraj.video_data.VideoSynchronizer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()
    private val musicViewModel: MusicViewModel by viewModels()

    @Inject
    lateinit var synchronizer: MusicSynchronizer

    @Inject
    lateinit var videoSync: VideoSynchronizer

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intentData = intent.data

        if (intentData != null) {
            getPath(Uri.parse(intentData.toString())).let {
                sharedViewModel.getMusicByPath(requireNotNull(it))
            }
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    sharedViewModel.music.collect {
                        if (it != null) {
                            Log.d("TAG", "onCreate: $it")
                            musicViewModel.addMediaItems(listOf(it))

                            musicViewModel.onEvent(
                                MusicEvent.OnMusicSelected(it)
                            )
                            musicViewModel.playSelectedMusic()
                        }

                    }
                }
            }
        }
        setContent {
            MMKitTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme
                        .background
                ) {

                    var permissionsGranted by remember {
                        mutableStateOf(false)
                    }
                    if (intent.data != null) {
                        MusicPlayerScreen(navController = rememberNavController())
                    } else
                        if (permissionsGranted) {
                            synchronizer.startSync()
                            videoSync.startSync()
                            RootNavigationGraph(navController = rememberNavController())
                        } else
                            Permissions {
                                permissionsGranted = it
                            }

                }
            }
        }
    }

    override fun onStop() {
        Log.d("TAG", "onStop: ")
        val intent = Intent(this, MusicService::class.java)
        intent.action = "STOP_SERVICE"
        startService(intent)
        super.onStop()
    }
    override fun onDestroy() {


        super.onDestroy()
    }
}



