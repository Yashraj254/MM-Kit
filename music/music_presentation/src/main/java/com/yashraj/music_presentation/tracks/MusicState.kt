package com.yashraj.music_presentation.tracks


import com.yashraj.music_domain.models.Music

data class MusicState(
    val musics: List<Music>? = emptyList(),
    val selectedMusic: Music? = null,
)
