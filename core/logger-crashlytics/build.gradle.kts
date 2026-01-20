plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.metro)
}

kotlin {
  android { namespace = "app.rickandmorty.core.logger.crashlytics" }

  dependencies {
    api(project(":core:startup"))

    implementation(libs.kermit)
  }

  sourceSets {
    androidMain { dependencies { implementation(libs.kermit.crashlytics) } }

    nativeMain { dependencies { implementation(libs.kermit.crashlytics) } }
  }
}
