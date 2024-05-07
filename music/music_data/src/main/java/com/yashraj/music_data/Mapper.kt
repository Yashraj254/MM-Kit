package com.yashraj.music_data

import androidx.media3.common.MediaItem
import com.yashraj.music_domain.models.Folder
import com.yashraj.music_domain.models.Music
import com.yashraj.music_domain.models.Playlist
import com.yashraj.utils.Utils

fun MediaItem.toMusic() =
    Music(
        album = "",
        albumId = 0L,
        artistId = 0L,
        dateAdded = 0,
        duration = 0,
        favorite = 0,
        folderName = "",
        folderPath = "",
        formattedDate = "",
        formattedDuration = "",
        formattedSize = "",
        size = 0,
        artist = mediaMetadata.artist.toString(),
        imageUri = mediaMetadata.artworkUri.toString(),
        mediaStoreId = 0L,
        path = mediaId,
        playlistId = 0,
        title = mediaMetadata.title.toString()
    )

fun com.yashraj.database.entities.MusicEntity.toMusic() = Music(
    album = album,
    albumId = albumId,
    artist = artist,
    artistId = artistId,
    dateAdded = dateAdded,
    duration = duration,
    favorite = favorite,
    folderName = folderName,
    folderPath = folderPath,
    formattedDate = Utils.formatDate(dateAdded.toLong()),
    formattedDuration = Utils.formatDurationMillis(duration.toLong()),
    formattedSize = Utils.formatFileSize(size.toLong()),
    imageUri = imageUri,
    mediaStoreId = mediaStoreId,
    path = path,
    playlistId = playlistId,
    size = size,
    title = title,
)
fun Music.toMusicEntity() = com.yashraj.database.entities.MusicEntity(
    album = album,
    albumId = albumId,
    artist = artist,
    artistId = artistId,
    dateAdded = dateAdded,
    duration = duration,
    favorite = favorite,
    folderName = folderName,
    folderPath = folderPath,
    imageUri = imageUri,
    mediaStoreId = mediaStoreId,
    path = path,
    playlistId = playlistId,
    size = size,
    title = title,
)

fun com.yashraj.database.entities.DirectoryEntity.toFolder() = Folder(
    mediaCount = mediaCount,
    modified = modified,
    name = name,
    path = path,
    size = size
)

fun com.yashraj.database.entities.PlaylistEntity.toPlaylist() = Playlist(
    playlistId = playlistId,
    playlist = playlist
)

fun Playlist.toPlaylistEntity() = com.yashraj.database.entities.PlaylistEntity(
    playlistId = playlistId,
    playlist = playlist
)

