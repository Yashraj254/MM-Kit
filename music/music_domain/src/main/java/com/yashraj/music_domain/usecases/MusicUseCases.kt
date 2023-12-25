package com.yashraj.music_domain.usecases

data class MusicUseCases(
    val addToFavorites: AddToFavorites,
    val addToPlaylist: AddToPlaylist,
    val createNewPlaylist: CreateNewPlaylist,
    val getAllMusic: GetAllMusic,
    val getMusicByPath: GetMusicByPath,
    val getMusicPlaylists: GetMusicPlaylists,
    val getPlaylistMusic: GetPlaylistMusic,
    val getMusicDirectories: GetMusicDirectories,
    val getFolderTracks: GetFolderTracks,
    val getMusicFavorites: GetMusicFavorites,
    val removeFromFavorites: RemoveFromFavorites
)
