plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.kotlin.android)
}

android {
    namespace = "app.rickandmorty.data.graphql.client"
}

dependencies {
    implementation(libs.apollo.normalizedcache.sqlite)
    implementation(libs.apollo.runtime)

    implementation(projects.core.coroutines)
}
