package app.rickandmorty.gradle.utils

import app.rickandmorty.gradle.util.configureGradleManagedDevices
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project

context (Project)
internal fun BaseExtension.configureAndroidTesting() {
    testOptions {
        animationsDisabled = true

        unitTests.isReturnDefaultValues = true

        configureGradleManagedDevices()
    }
}
