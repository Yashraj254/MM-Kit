package com.yashraj.music_domain.di

import com.yashraj.music_domain.repository.MusicRepository
import com.yashraj.music_domain.service.PlaybackController
import com.yashraj.music_domain.usecases.player_usecases.AddMediaItems
import com.yashraj.music_domain.usecases.player_usecases.DestroyMediaController
import com.yashraj.music_domain.usecases.player_usecases.GetCurrentMusicPosition
import com.yashraj.music_domain.usecases.player_usecases.MusicPlayerUseCases
import com.yashraj.music_domain.usecases.player_usecases.PauseMusic
import com.yashraj.music_domain.usecases.player_usecases.PlayMusic
import com.yashraj.music_domain.usecases.player_usecases.ResumeMusic
import com.yashraj.music_domain.usecases.player_usecases.SeekMusicPosition
import com.yashraj.music_domain.usecases.player_usecases.SetMediaControllerCallback
import com.yashraj.music_domain.usecases.player_usecases.SetMusicShuffleEnabled
import com.yashraj.music_domain.usecases.player_usecases.SetPlayerRepeatOneEnabled
import com.yashraj.music_domain.usecases.player_usecases.SkipNextMusic
import com.yashraj.music_domain.usecases.player_usecases.SkipPreviousMusic
import com.yashraj.music_domain.usecases.GetAllMusic
import com.yashraj.music_domain.usecases.GetFolderTracks
import com.yashraj.music_domain.usecases.GetMusicDirectories
import com.yashraj.music_domain.usecases.GetMusicFavorites
import com.yashraj.music_domain.usecases.MusicUseCases
import com.yashraj.music_domain.usecases.AddToFavorites
import com.yashraj.music_domain.usecases.AddToPlaylist
import com.yashraj.music_domain.usecases.CreateNewPlaylist
import com.yashraj.music_domain.usecases.GetMusicByPath
import com.yashraj.music_domain.usecases.GetMusicPlaylists
import com.yashraj.music_domain.usecases.GetPlaylistMusic
import com.yashraj.music_domain.usecases.RemoveFromFavorites
import com.yashraj.music_domain.usecases.player_usecases.GetCurrentMusic
import com.yashraj.music_domain.usecases.player_usecases.GetMediaController
import com.yashraj.music_domain.usecases.player_usecases.GetPlayerState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MusicDomainModule {

    @Singleton
    @Provides
    fun provideMusicUseCases(
        repository: MusicRepository,
    ): MusicUseCases {
        return MusicUseCases(
            addToFavorites = AddToFavorites(repository),
            addToPlaylist = AddToPlaylist(repository),
            createNewPlaylist = CreateNewPlaylist(repository),
            getAllMusic = GetAllMusic(repository),
            getMusicByPath = GetMusicByPath(repository),
            getMusicPlaylists = GetMusicPlaylists(repository),
            getPlaylistMusic = GetPlaylistMusic(repository),
            getMusicDirectories = GetMusicDirectories(repository),
            getFolderTracks = GetFolderTracks(repository),
            getMusicFavorites = GetMusicFavorites(repository),
            removeFromFavorites = RemoveFromFavorites(repository)
        )
    }

    @Singleton
    @Provides
    fun provideMusicPlayerUseCases(
        playbackController: PlaybackController
    ): MusicPlayerUseCases {
        return MusicPlayerUseCases(
            addMediaItems = AddMediaItems(playbackController),
            destroyMediaController = DestroyMediaController(playbackController),
            getCurrentMusicPosition = GetCurrentMusicPosition(playbackController),
            getCurrentMusic = GetCurrentMusic(playbackController),
            getMediaController = GetMediaController(playbackController),
            getPlayerState = GetPlayerState(playbackController),
            pauseMusic = PauseMusic(playbackController),
            playMusic = PlayMusic(playbackController),
            resumeMusic = ResumeMusic(playbackController),
            seekMusicPosition = SeekMusicPosition(playbackController),
            setMediaControllerCallback = SetMediaControllerCallback(playbackController),
            setMusicShuffleEnabled = SetMusicShuffleEnabled(playbackController),
            setPlayerRepeatOneEnabled = SetPlayerRepeatOneEnabled(playbackController),
            skipNextMusic = SkipNextMusic(playbackController),
            skipPreviousMusic = SkipPreviousMusic(playbackController)
        )
    }
}
