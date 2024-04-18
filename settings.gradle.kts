pluginManagement {
    repositories {
        google {
            content {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
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
        google {
            content {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("com.gradle.develocity") version "3.17.2"
    id("com.gradle.common-custom-user-data-gradle-plugin") version "2.0.1"
}

develocity {
    buildScan {
        termsOfUseUrl = "https://gradle.com/help/legal-terms-of-use"
        termsOfUseAgree = "yes"

        val isCI = providers.environmentVariable("CI").orNull == "true"
        publishing.onlyIf { isCI }
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "rickandmorty"

include(":app")

include(":baseline-profile")

include(":core:coil")
include(":core:content-view")
include(":core:coroutines")
include(":core:design-system")
include(":core:hilt-test")
include(":core:l10n")
include(":core:logging")
include(":core:metrics")
include(":core:okhttp")
include(":core:resource-state")
include(":core:startup")
include(":core:strict-mode")
include(":core:ui")

include(":data:character")
include(":data:database")
include(":data:episode")
include(":data:graphql-client")
include(":data:graphql-schema")
include(":data:locale")
include(":data:locale-proto")
include(":data:location")
include(":data:model")
include(":data:paging")
include(":data:theme")
include(":data:theme-proto")

include(":features:characters")
include(":features:episodes")
include(":features:locations")
include(":features:settings")
