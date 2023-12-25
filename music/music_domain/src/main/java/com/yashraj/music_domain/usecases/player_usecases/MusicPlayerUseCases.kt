package com.yashraj.music_domain.usecases.player_usecases

data class MusicPlayerUseCases(
    val addMediaItems: AddMediaItems,
    val destroyMediaController: DestroyMediaController,
    val getCurrentMusicPosition: GetCurrentMusicPosition,
    val getCurrentMusic: GetCurrentMusic,
    val getMediaController: GetMediaController,
    val getPlayerState: GetPlayerState,
    val pauseMusic: PauseMusic,
    val playMusic: PlayMusic,
    val resumeMusic: ResumeMusic,
    val seekMusicPosition: SeekMusicPosition,
    val setMediaControllerCallback: SetMediaControllerCallback,
    val setMusicShuffleEnabled: SetMusicShuffleEnabled,
    val setPlayerRepeatOneEnabled: SetPlayerRepeatOneEnabled,
    val skipNextMusic: SkipNextMusic,
    val skipPreviousMusic: SkipPreviousMusic
)
