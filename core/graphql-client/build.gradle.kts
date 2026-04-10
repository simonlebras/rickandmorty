plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.core.graphql.client" }

  dependencies {
    api(project(":core:coroutines"))
    api(project(":core:metro-common"))

    api(libs.apollo.runtime)

    implementation(libs.apollo.ktor)
  }
}
