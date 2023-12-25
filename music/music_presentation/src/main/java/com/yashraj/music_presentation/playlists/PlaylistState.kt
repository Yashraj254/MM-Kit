package com.yashraj.music_presentation.playlists

import com.yashraj.music_domain.models.Music
import com.yashraj.music_domain.models.Playlist

data class PlaylistState(
    val playlists: List<Playlist> = emptyList(),
    val selectedPlaylist: Playlist? = null
)
