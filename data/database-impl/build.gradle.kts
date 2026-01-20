import app.rickandmorty.gradle.dsl.kspDependenciesForAllTargets

plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.androidx.room)
  alias(libs.plugins.ksp)
  alias(libs.plugins.metro)
}

kotlin {
  android {
    namespace = "app.rickandmorty.data.database.impl"

    @Suppress("UnstableApiUsage") optimization { consumerKeepRules.files("consumer-rules.pro") }

    lint { disable += "RestrictedApi" }
  }

  dependencies {
    api(project(":data:database-api"))

    api(libs.androidx.room.runtime)
    api(libs.androidx.sqlite.bundled)

    implementation(project(":core:coroutines"))
    implementation(project(":core:metro-common"))

    implementation(libs.androidx.room.paging)
  }

  sourceSets { jvmMain { dependencies { implementation(libs.okio) } } }

  kspDependenciesForAllTargets { ksp(libs.androidx.room.compiler) }
}

lint { disable += "RestrictedApi" }

room { schemaDirectory("$projectDir/schemas") }
