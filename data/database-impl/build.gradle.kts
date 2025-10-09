import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.androidx.room)
  alias(libs.plugins.ksp)
  alias(libs.plugins.metro)
}

kotlin {
  androidLibrary {
    namespace = "app.rickandmorty.data.database.impl"

    @Suppress("UnstableApiUsage") optimization { consumerKeepRules.files("consumer-rules.pro") }

    lint { disable += "RestrictedApi" }
  }

  @OptIn(ExperimentalKotlinGradlePluginApi::class)
  dependencies {
    api(project(":data:database-api"))

    api(libs.androidx.room.runtime)
    api(libs.androidx.sqlite.bundled)

    implementation(project(":core:coroutines"))
    implementation(project(":core:metro"))

    implementation(libs.androidx.room.paging)
  }

  sourceSets { jvmMain { dependencies { implementation(libs.okio) } } }
}

lint { disable += "RestrictedApi" }

room { schemaDirectory("$projectDir/schemas") }

dependencies {
  add("kspAndroid", libs.androidx.room.compiler)
  add("kspIosArm64", libs.androidx.room.compiler)
  add("kspIosSimulatorArm64", libs.androidx.room.compiler)
  add("kspJvm", libs.androidx.room.compiler)
}
