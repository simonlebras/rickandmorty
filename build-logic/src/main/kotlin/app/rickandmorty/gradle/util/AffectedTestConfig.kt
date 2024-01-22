package app.rickandmorty.gradle.util

import com.dropbox.affectedmoduledetector.AffectedTestConfiguration
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureAffectedAndroidTest() {
    configure<AffectedTestConfiguration> {
        val gradleManagedDevice = providers.gradleProperty("affectedGradleManagedDevice").orNull
        if (!gradleManagedDevice.isNullOrEmpty()) {
            runAndroidTestTask = "${gradleManagedDevice}DebugAndroidTest"
        }
    }
}
