import com.apollographql.apollo3.gradle.internal.ApolloGenerateSourcesFromIrTask
import com.autonomousapps.tasks.CodeSourceExploderTask

plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.autodagger)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.kotlin.android)

    alias(libs.plugins.apollo)
}

android {
    namespace = "app.rickandmorty.data.episode"
}

apollo {
    service("rickandmorty") {
        packageName.set("app.rickandmorty.data.episode")
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

// https://github.com/gradle/gradle/issues/25885
tasks.withType<CodeSourceExploderTask>().configureEach {
    dependsOn(tasks.withType<ApolloGenerateSourcesFromIrTask>())
}
