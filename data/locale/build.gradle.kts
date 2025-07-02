plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.kotlin.android)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

android { namespace = "app.rickandmorty.data.locale" }

dependencies {
  api(project(":data:model"))

  api(libs.kotlinx.collectionsimmutable)

  implementation(project(":core:base"))
  implementation(project(":core:coroutines"))
  implementation(project(":core:startup"))

  implementation(project(":data:locale-proto"))

  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.datastore)
}
