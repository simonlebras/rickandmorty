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
  id("com.gradle.develocity") version "4.1.1"
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

rootProject.name = "rickandmorty"

includeBuild("build-logic")

include(
  ":app",
  ":baseline-profile",
  ":core:base",
  ":core:coil",
  ":core:coil-logger",
  ":core:compose-debug",
  ":core:coroutines",
  ":core:crashlytics",
  ":core:design-system",
  ":core:ktor",
  ":core:l10n",
  ":core:logger-crashlytics",
  ":core:logger-debug",
  ":core:metro",
  ":core:process-lifecycle",
  ":core:resource-state",
  ":core:resources-app",
  ":core:startup",
  ":core:strict-mode",
  ":core:ui",
  ":core:ui-tooling-preview",
  ":data:character",
  ":data:database",
  ":data:episode",
  ":data:filesystem",
  ":data:graphql-client",
  ":data:graphql-schema",
  ":data:locale",
  ":data:locale-proto",
  ":data:location",
  ":data:model",
  ":data:paging",
  ":data:theme-api",
  ":data:theme-impl",
  ":ui:character-list",
  ":ui:episode-list",
  ":ui:location-list",
  ":ui:settings-common",
  ":ui:settings-language",
  ":ui:settings-main",
  ":ui:settings-theme",
)
