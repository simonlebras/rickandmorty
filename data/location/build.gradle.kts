plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.autodagger)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.kotlin.android)

    alias(libs.plugins.apollo)
}

android {
    namespace = "app.rickandmorty.data.location"
}

apollo {
    service("rickandmorty") {
        packageName.set("app.rickandmorty.data.location")
        generateApolloMetadata.set(true)
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
