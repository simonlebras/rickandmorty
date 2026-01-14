plugins {
  alias(libs.plugins.rickandmorty.root)

  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.android.lint) apply false
  alias(libs.plugins.android.multiplatformlibrary) apply false
  alias(libs.plugins.android.test) apply false
  alias(libs.plugins.androidx.baselineprofile) apply false
  alias(libs.plugins.androidx.room) apply false
  alias(libs.plugins.apollo) apply false
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.compose.multiplatform) apply false
  alias(libs.plugins.dependencyanalysis) apply false
  alias(libs.plugins.firebase.crashlytics) apply false
  alias(libs.plugins.firebase.perf) apply false
  alias(libs.plugins.googleservices) apply false
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.ktfmt) apply false
  alias(libs.plugins.metro) apply false
  alias(libs.plugins.sortdependencies) apply false
  alias(libs.plugins.tapmoc) apply false
}
