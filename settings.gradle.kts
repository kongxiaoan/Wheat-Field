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

rootProject.name = "LocaleFlexPicker"
include(":app")
include(":LocaleFlexPicker")
include(":I18nProvider")
include(":deeplink-project")
include(":deeplink-project:test-deeplink-app")
