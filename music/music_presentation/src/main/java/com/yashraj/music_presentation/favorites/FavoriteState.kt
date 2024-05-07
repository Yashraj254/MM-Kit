package com.yashraj.music_presentation.favorites

import com.yashraj.music_domain.models.Music

data class FavoriteState(
    val musics: List<String>? = emptyList()
)