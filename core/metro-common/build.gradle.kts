plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin {
  android { namespace = "app.rickandmorty.core.metro.common" }

  dependencies {
    implementation(libs.androidx.lifecycle.viewmodel)

    implementation(libs.metro.runtime)
  }
}
