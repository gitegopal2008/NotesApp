pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NotesApp"
include(":app")
include(":core")
include(":domain")
include(":data")
include(":designsystem")
include(":security")
include(":analytics")
include(":features:notes")
include(":features:folders")
include(":features:search")
include(":features:settings")
include(":features:backup")
include(":features:reminders")
