package app.rickandmorty.gradle.utils

import com.dropbox.affectedmoduledetector.AffectedTestConfiguration
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureAffectedAndroidTest() {
    configure<AffectedTestConfiguration> {
        val gradleManagedDevice = findProperty("affectedGradleManagedDevice") as? String
        if (!gradleManagedDevice.isNullOrEmpty()) {
            runAndroidTestTask = "${gradleManagedDevice}DebugAndroidTest"
        }
    }
}
