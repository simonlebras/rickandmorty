import com.autonomousapps.tasks.CodeSourceExploderTask

plugins {
    alias(libs.plugins.rickandmorty.android.library)
    alias(libs.plugins.rickandmorty.compose)
    alias(libs.plugins.rickandmorty.kotlin.multiplatform)
    alias(libs.plugins.rickandmorty.spotless)

    alias(libs.plugins.dependencyanalysis)
    alias(libs.plugins.sortdependencies)
}

android {
    namespace = "app.rickandmorty.core.designsystem"
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(compose.material3)

                implementation(compose.components.resources)
                implementation(compose.materialIconsExtended)

                implementation(libs.androidx.annotation)

                implementation(libs.coil.compose)
            }
        }
    }
}

compose.resources {
    packageOfResClass = "app.rickandmorty.core.designsystem.resources"
}

// https://github.com/gradle/gradle/issues/25885
tasks.withType<CodeSourceExploderTask>().configureEach {
    dependsOn("generateActualResourceCollectorsForAndroidMain")
}
