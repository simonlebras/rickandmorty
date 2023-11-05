pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.google.android.gms.oss-licenses-plugin") {
                useModule("com.google.android.gms:oss-licenses-plugin:${requested.version}")
            }
        }
    }

    includeBuild("build-logic")
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        maven { url = uri("https://androidx.dev/snapshots/builds/10915585/artifacts/repository/") }
        maven { url = uri("https://androidx.dev/storage/compose-compiler/repository/") }
    }
}

plugins {
    id("com.gradle.enterprise") version "3.15.1"
    id("com.gradle.common-custom-user-data-gradle-plugin") version "1.12"
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
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
include(":core:contentview")
include(":core:core")
include(":core:coroutines")
include(":core:designsystem")
include(":core:graphql-client")
include(":core:graphql-schema")
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

include(":features:characters")
include(":features:episodes")
include(":features:locations")
include(":features:settings")
