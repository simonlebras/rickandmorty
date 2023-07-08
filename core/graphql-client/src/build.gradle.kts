plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
}

android {
    namespace = "app.rickandmorty.graphql.client"
}

dependencies {
    implementation(libs.apollo.normalizedCache.sqlite)
    implementation(libs.apollo.runtime)

    implementation(projects.core.coroutines)
    implementation(projects.core.hilt)
}
