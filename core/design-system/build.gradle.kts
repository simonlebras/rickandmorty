import com.autonomousapps.tasks.CodeSourceExploderTask

plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.compose)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

kotlin {
  androidLibrary { namespace = "app.rickandmorty.core.designsystem" }

  sourceSets {
    commonMain {
      dependencies {
        api(compose.material3)

        implementation(compose.components.resources)

        implementation(libs.androidx.annotation)

        implementation(libs.coil.compose)

        implementation(libs.compose.placeholder.material3)
      }
    }
  }
}

compose.resources { packageOfResClass = "app.rickandmorty.core.designsystem.resources" }

// https://github.com/gradle/gradle/issues/25885
tasks.withType<CodeSourceExploderTask>().configureEach {
  dependsOn("generateActualResourceCollectorsForAndroidMain")
}
