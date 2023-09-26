pluginManagement {
    includeBuild("build-logic")
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
include(":music:music_player:player_data")
include(":music:music_player:player_domain")
include(":music:music_player:player_presentation")
include(":music:music_settings:settings_domain")
include(":music:music_settings:settings_data")
include(":music:music_settings:settings_presentation")
include(":video:video_settings:settings_domain")
include(":video:video_settings:settings_data")
include(":video:video_settings:settings_presentation")
include(":video:video_player:player_domain")
include(":video:video_player:player_data")
include(":video:video_player:player_presentation")
include(":core:ui")
include(":core:utils")
include(":core:database")
include(":core:datastore")
include(":core:di")
include(":music:music_navigations")

include(":music:music_presentation")
include(":music:music_data")
include(":music:music_domain")
include(":video:video_data")
include(":video:video_domain")
include(":video:video_presentation")
