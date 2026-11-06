import app.rickandmorty.gradle.dsl.kspDependenciesForAllTargets

plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)

  alias(libs.plugins.androidx.room)
  alias(libs.plugins.ksp)
}

kotlin {
  android {
    namespace = "app.rickandmorty.data.database.impl"

    @Suppress("UnstableApiUsage") optimization { consumerKeepRules.files("consumer-rules.pro") }

    lint { disable += "RestrictedApi" }
  }

  compilerOptions { optIn.add("org.mobilenativefoundation.store6.core.ExperimentalStoreApi") }

  dependencies {
    api(project(":core:coroutines"))
    api(project(":core:metro-common"))

    api(project(":data:database-api"))
    api(project(":data:store"))

    api(libs.androidx.room.runtime)
    api(libs.androidx.sqlite.bundled)

    api(libs.store.room)
  }

  sourceSets {
    jvmMain {
      dependencies { implementation(libs.okio) }
    }
  }

  kspDependenciesForAllTargets { ksp(libs.androidx.room.compiler) }
}

room3 { schemaDirectory("$projectDir/schemas") }
