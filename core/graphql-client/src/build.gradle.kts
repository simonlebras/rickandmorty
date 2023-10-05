plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.hilt)
}

android {
    namespace = "app.rickandmorty.graphql.client"
}

dependencies {
    implementation(libs.apollo.normalizedCache.sqlite)
    implementation(libs.apollo.runtime)

    implementation(projects.core.coroutines)
}
