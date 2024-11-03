pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

rootProject.name = "DevTodoNote-Multimodule"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:data")
include(":core:domain")
include(":core:ui")
include(":core:common")
include(":core:navigator")
include(":core:feature-base")
include(":auth:data")
include(":auth:domain")
include(":auth:presentation")
include(":home:presentation")
include(":home:data")
include(":home:domain")
include(":note:data")
include(":note:domain")
include(":note:presentation")
include(":core:database")
