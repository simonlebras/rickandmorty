plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
}

kotlin { dependencies { api(libs.androidx.paging.common) } }

dependencyAnalysis { issues { onUnusedDependencies { exclude(libs.androidx.paging.common) } } }
