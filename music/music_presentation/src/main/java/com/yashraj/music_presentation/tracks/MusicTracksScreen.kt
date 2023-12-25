package com.yashraj.music_presentation.tracks

import android.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.yashraj.music_presentation.components.MusicItem


@Composable
fun MusicTracksScreen(
    showPlayer: (Boolean) -> Unit,
    musicState: MusicState,
    musicViewModel: MusicViewModel = hiltViewModel(),
) {

    val musics = musicState.musics


        if (musics != null)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(musics.size) { i ->
                    val music = musics[i]
                    MusicItem(
                        music = music,
                        imageVector = Icons.Default.MusicNote
                    ) {
                        showPlayer(true)
                        musicViewModel.addMediaItems(musics)
                        musicViewModel.onEvent(MusicEvent.OnMusicSelected(music))
                        musicViewModel.onEvent(MusicEvent.PlayMusic)
                    }
                }
            }

}

@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun MusicTracksScreenPreview() {
//    MusicTracksScreen()
}
