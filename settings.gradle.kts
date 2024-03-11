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

rootProject.name = "Wheat-Field"
include(":app")
include(":LocaleFlexPicker")
include(":I18nProvider")
include(":deeplink-project")
include(":deeplink-project:test-deeplink-app")
include(":video-list:Video-HighQuality-cacherkit")
include(":video-list:Video-HighQuality-commonkit")
include(":video-list:Video-HighQuality-playerkit")
include(":video-list:Video-HighQuality-uikit")
include(":video-list:video-simple")
