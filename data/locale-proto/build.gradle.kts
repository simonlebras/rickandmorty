plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.wire)
}

wire { kotlin {} }
