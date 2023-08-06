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
    }
}

plugins {
    id("com.gradle.enterprise") version "3.14.1"
    id("com.gradle.common-custom-user-data-gradle-plugin") version "1.11.1"
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.6.0"
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
include(":core:graphql-client")
include(":core:graphql-schema")
include(":core:hilt")
include(":core:hilt-test")
include(":core:jankstats")
include(":core:locale")
include(":core:logging")
include(":core:okhttp")
include(":core:resource-state")
include(":core:startup")
include(":core:strictmode")
include(":core:theme")
include(":core:theme-proto")
include(":core:ui")
include(":core:ui-resources")

include(":feature:characters")
include(":feature:episodes")
include(":feature:home")
include(":feature:locations")
include(":feature:settings")
