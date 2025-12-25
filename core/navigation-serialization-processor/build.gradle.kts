plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.jvm.library)
}

dependencies {
  implementation(project(":core:navigation-serialization-runtime"))

  implementation(libs.kotlinpoet)
  implementation(libs.kotlinpoet.ksp)

  implementation(libs.ksp.api)

  implementation(libs.metro.runtime)
}
