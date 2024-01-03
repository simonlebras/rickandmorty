plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.autodagger)
    alias(libs.plugins.rickandmorty.hilt)
    alias(libs.plugins.rickandmorty.kotlin.android)
    alias(libs.plugins.rickandmorty.room)
}

android {
    namespace = "app.rickandmorty.data.database"
}

dependencies {
    api(projects.data.model)

    implementation(libs.androidx.room.paging)

    implementation(libs.autodagger.room)
}

dependencyAnalysis {
    issues {
        onCompileOnly {
            exclude("se.ansman.dagger.auto:androidx-room")
        }
    }
}
