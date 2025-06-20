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
}

dependencyResolutionManagement {
  repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
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
  id("com.gradle.develocity") version "4.0.2"
  id("com.gradle.common-custom-user-data-gradle-plugin") version "2.3"
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

includeBuild("build-logic")

include(":app")

include(":baseline-profile")

include(":core:base")

include(":core:coil")

include(":core:coil-logger")

include(":core:compose-debug")

include(":core:coroutines")

include(":core:crashlytics")

include(":core:design-system")

include(":core:ktor")

include(":core:l10n")

include(":core:logger-crashlytics")

include(":core:logger-debug")

include(":core:metro")

include(":core:process-lifecycle")

include(":core:resource-state")

include(":core:resources-app")

include(":core:startup")

include(":core:strict-mode")

include(":core:ui")

include(":core:ui-tooling-preview")

include(":data:character")

include(":data:database")

include(":data:episode")

include(":data:filesystem")

include(":data:graphql-client")

include(":data:graphql-schema")

include(":data:locale")

include(":data:locale-proto")

include(":data:location")

include(":data:model")

include(":data:paging")

include(":data:theme-api")

include(":data:theme-impl")

include(":thirdparty:androidx-paging-compose")

include(":ui:character-list")

include(":ui:episode-list")

include(":ui:location-list")

include(":ui:settings-common")

include(":ui:settings-language")

include(":ui:settings-main")

include(":ui:settings-theme")
