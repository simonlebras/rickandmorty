plugins {
  alias(libs.plugins.rickandmorty.android.test)
  alias(libs.plugins.rickandmorty.codehealth)

  alias(libs.plugins.androidx.baselineprofile)
}

android {
  namespace = "app.rickandmorty.baselineprofile"

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

  implementation(libs.kotlin.test.junit)
}

dependencyAnalysis {
  issues {
    onRuntimeOnly { exclude(libs.kotlin.test.junit) }
    onUnusedDependencies { exclude(libs.androidx.test.junit) }
  }
}
