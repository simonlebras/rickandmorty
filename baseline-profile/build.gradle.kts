plugins {
  alias(libs.plugins.rickandmorty.android.test)
  alias(libs.plugins.rickandmorty.kotlin.android)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.androidx.baselineprofile)
  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

android {
  namespace = "app.rickandmorty.baselineprofile"

  defaultConfig { testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" }

  targetProjectPath = ":app"
}

baselineProfile {
  managedDevices += "pixel6api31aosp"
  useConnectedDevices = false
}

dependencies {
  implementation(libs.androidx.benchmark.macro)
  implementation(libs.androidx.test.junit)
  implementation(libs.androidx.test.runner)

  implementation(libs.junit4)
}

dependencyAnalysis { issues { onUnusedDependencies { exclude(libs.androidx.test.junit) } } }
