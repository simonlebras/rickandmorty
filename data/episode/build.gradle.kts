plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.apollo)
    alias(libs.plugins.rickandmorty.kotlin.android)
    alias(libs.plugins.rickandmorty.spotless)

    alias(libs.plugins.dependencyanalysis)
    alias(libs.plugins.sortdependencies)
}

android {
    namespace = "app.rickandmorty.data.episode"
}

apollo {
    service("rickandmorty") {
        packageName = "app.rickandmorty.data.episode"
        dependsOn(projects.data.graphqlSchema)
    }
}

dependencies {
    api(libs.androidx.paging.common)

    api(projects.data.graphqlSchema)
    api(projects.data.model)

    implementation(libs.apollo.runtime)

    implementation(projects.data.database)
    implementation(projects.data.paging)
}
