package com.yashraj.music_presentation.tracks


import com.yashraj.music_domain.models.Music

sealed class MusicEvent {
    data object PlayMusic : MusicEvent()
    data object ResumeMusic : MusicEvent()
    data object PauseMusic : MusicEvent()
    data class OnMusicSelected(val selectedMusic: Music) : MusicEvent()
}
