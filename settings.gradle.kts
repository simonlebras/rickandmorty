pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    includeBuild("build-logic")
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://androidx.dev/storage/compose-compiler/repository")
    }
}

plugins {
    id("com.gradle.enterprise") version "3.13.4"
    id("com.gradle.common-custom-user-data-gradle-plugin") version "1.11"
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlwaysIf(System.getenv("CI") == "true")
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "rickandmorty"

include(":app")

include(":baselineprofile")

include(":core:coil")
include(":core:core")
include(":core:coroutines")
include(":core:designsystem")
include(":core:graphql")
include(":core:hilt")
include(":core:jankstats")
include(":core:locale")
include(":core:logging")
include(":core:okhttp")
include(":core:resource-state")
include(":core:strictmode")
include(":core:ui")
include(":core:ui-resources")

include(":feature:characters")
include(":feature:episodes")
include(":feature:home")
include(":feature:locations")
include(":feature:settings")
