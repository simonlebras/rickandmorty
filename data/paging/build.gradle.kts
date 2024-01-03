plugins {
    alias(libs.plugins.rickandmorty.jvm.library)
    alias(libs.plugins.rickandmorty.kotlin.jvm)
}

dependencies {
    api(libs.androidx.paging.common)
}
