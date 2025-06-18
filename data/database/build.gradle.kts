plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.android)
}

android { namespace = "app.rickandmorty.data.database" }

dependencies {
  api(project(":data:model"))
  api(project(":data:paging"))
}
