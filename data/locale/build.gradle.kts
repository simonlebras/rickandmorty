plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.kotlin.android)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

android { namespace = "app.rickandmorty.data.locale" }

dependencies {
  api(libs.kotlinx.collectionsimmutable)

  api(projects.data.model)

  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.datastore)

  implementation(projects.core.base)
  implementation(projects.core.coroutines)
  implementation(projects.core.startup)

  implementation(projects.data.localeProto)
}
