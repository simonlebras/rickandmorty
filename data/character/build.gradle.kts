plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.apollo)
    alias(libs.plugins.rickandmorty.autodagger)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.kotlin.android)
}

android {
    namespace = "app.rickandmorty.data.character"
}

apollo {
    service("rickandmorty") {
        packageName = "app.rickandmorty.data.character"
        dependsOn(projects.data.graphqlSchema)
    }
}

dependencies {
    api(libs.androidx.paging.common)

    api(projects.data.database)
    api(projects.data.graphqlSchema)
    api(projects.data.model)

    implementation(libs.apollo.runtime)

    implementation(projects.data.paging)
}
