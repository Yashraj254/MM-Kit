pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MM Kit"
include(":app")
include(":music:music_tracks:tracks_data")
include(":music:music_tracks:tracks_domain")
include(":music:music_tracks:tracks_presentation")
include(":music:music_playlist:playlist_data")
include(":music:music_playlist:playlist_domain")
include(":music:music_playlist:playlist_presentation")
include(":music:music_favorites:favorites_presentation")
include(":music:music_favorites:favorites_domain")
include(":music:music_favorites:favorites_data")
include(":music:music_folders:folders_presentation")
include(":music:music_folders:folders_data")
include(":music:music_folders:folders_domain")
include(":music:music_player:player_data")
include(":music:music_player:player_domain")
include(":music:music_player:player_presentation")
include(":music:music_settings:settings_domain")
include(":music:music_settings:settings_data")
include(":music:music_settings:settings_presentation")
include(":video:video_settings:settings_domain")
include(":video:video_settings:settings_data")
include(":video:video_settings:settings_presentation")
include(":video:video_folders:folders_domain")
include(":video:video_folders:folders_data")
include(":video:video_folders:folders_presentation")
include(":video:video_player:player_domain")
include(":video:video_player:player_data")
include(":video:video_player:player_presentation")
include(":video:video_tracks:tracks_domain")
include(":video:video_tracks:tracks_data")
include(":video:video_tracks:tracks_presentation")
include(":common:ui")
include(":common:utils")
include(":common:database")
include(":common:datastore")
include(":common:di")
