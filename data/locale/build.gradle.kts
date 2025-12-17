plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.codehealth)
}

android { namespace = "app.rickandmorty.data.locale" }

dependencies {
  api(project(":data:model"))

  api(libs.kotlinx.collectionsimmutable)

  implementation(project(":core:base"))
  implementation(project(":core:coroutines"))
  implementation(project(":core:startup"))

  implementation(libs.androidx.appcompat)
}
