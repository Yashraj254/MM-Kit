package com.yashraj.video_presentation.tracks

import android.content.Intent
import android.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yashraj.video_domain.models.Video
import com.yashraj.video_presentation.components.VideoItem
import com.yashraj.video_presentation.player.VideoPlayerActivity


@Composable
fun VideoTracksScreen(
    videos:List<Video>,
    fetchAllVideos:Boolean = false
) {
    val context = LocalContext.current
    Box {
        if (videos.isNotEmpty())
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(videos.size) { i ->
                    val video = videos[i]
                    VideoItem(
                        video = video,
                        imageVector = Icons.Default.MusicNote,
                        tint = androidx.compose.ui.graphics.Color.Unspecified,
                        onClick = {
                            val intent = Intent(context,VideoPlayerActivity::class.java)
                            intent.putExtra("uri",video.imageUri)
                            intent.putExtra("folder",video.folderPath)
                            intent.putExtra("fetchAll",fetchAllVideos)
                            context.startActivity(intent)
                        }
                    )
                }
            }
    }
}

@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun MusicTracksScreenPreview() {
//    MusicTracksScreen()
}
