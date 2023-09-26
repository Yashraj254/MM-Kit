package com.yashraj.mmkit

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.yashraj.mmkit.ui.theme.MMKitTheme
import com.yashraj.music_presentation.tracks.SharedViewModel
import com.yashraj.music_data.service.MusicService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()

    private val storagePermission = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> Manifest.permission.READ_MEDIA_VIDEO
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> Manifest.permission.READ_EXTERNAL_STORAGE
        else -> Manifest.permission.WRITE_EXTERNAL_STORAGE
    }

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MMKitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme
                        .background
                ) {
                    val storagePermissionState = rememberPermissionState(
                        permission = storagePermission
                    )
                    val navController = rememberNavController()
                    val lifecycleOwner = LocalLifecycleOwner.current

                    DisposableEffect(key1 = lifecycleOwner) {
                        val observer = LifecycleEventObserver { _, event ->
                            if (event == Lifecycle.Event.ON_START) {
                                storagePermissionState.launchPermissionRequest()
                            }
                        }
                        lifecycleOwner.lifecycle.addObserver(observer)
                        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
                    }

                    LaunchedEffect(key1 = storagePermissionState.status.isGranted) {
                        if (storagePermissionState.status.isGranted) {
//                            synchronizer.startSync()
                        }
                    }
//                    MusicScreens(sharedViewModel)
                    RootNavigationGraph(navController = rememberNavController())

                }
            }
        }
//        if (!checkPermission()) {
//            requestPermission()
//            return
//        }
    }

    override fun onDestroy() {
        super.onDestroy()

        sharedViewModel.destroyMediaController()
        stopService(Intent(this, MusicService::class.java))
    }

//    private fun checkPermission(): Boolean {
//        val result = ContextCompat.checkSelfPermission(
//            this,
//            android.Manifest.permission.READ_EXTERNAL_STORAGE
//        )
//        return result == PackageManager.PERMISSION_GRANTED
//    }
//
//    private fun requestPermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(
//                this,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE
//            )
//        )
//            Toast.makeText(
//                this,
//                "READ PERMISSION IS REQUIRED, PLEASE ALLOW FROM SETTINGS",
//                Toast.LENGTH_SHORT
//            ).show()
//        else
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 123
//            )
//    }
}



