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
  val agpVersion =
    File("gradle/libs.versions.toml").useLines { lines ->
      lines
        .first { it.trim().startsWith("android-plugin") }
        .substringAfter("=")
        .trim()
        .removeSurrounding("\"")
    }
  id("com.android.settings") version agpVersion

  id("com.gradle.develocity") version "4.2.2"
  id("com.gradle.common-custom-user-data-gradle-plugin") version "2.4.0"
}

android {
  compileSdk { version = release(36) }
  minSdk { version = release(28) }
  targetSdk { version = release(36) }
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
  ":core:metro-common",
  ":core:navigation",
  ":core:process-lifecycle",
  ":core:resource-state",
  ":core:resources-app",
  ":core:startup",
  ":core:strict-mode",
  ":core:ui",
  ":core:ui-tooling-preview",
  ":data:character-api",
  ":data:character-impl",
  ":data:database-api",
  ":data:database-impl",
  ":data:episode-api",
  ":data:episode-impl",
  ":data:filesystem",
  ":data:graphql-client",
  ":data:graphql-schema",
  ":data:locale",
  ":data:location-api",
  ":data:location-impl",
  ":data:model",
  ":data:paging",
  ":data:theme-api",
  ":data:theme-impl",
  ":ui:character-api",
  ":ui:character-impl",
  ":ui:episode-api",
  ":ui:episode-impl",
  ":ui:location-api",
  ":ui:location-impl",
  ":ui:settings-common",
  ":ui:settings-language",
  ":ui:settings-main",
  ":ui:settings-theme",
)
