package app.rickandmorty.gradle.dsl

import app.rickandmorty.gradle.util.configureGradleManagedDevices
import com.android.build.api.dsl.KotlinMultiplatformAndroidDeviceTest
import com.android.build.api.dsl.KotlinMultiplatformAndroidHostTest
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project

public fun KotlinMultiplatformAndroidLibraryTarget.hostTest(
  action: KotlinMultiplatformAndroidHostTest.() -> Unit = {}
) {
  withHostTest {
    isIncludeAndroidResources = true

    action()
  }
}

context(project: Project)
public fun KotlinMultiplatformAndroidLibraryTarget.deviceTest(
  action: KotlinMultiplatformAndroidDeviceTest.() -> Unit = {}
) {
  val libs = project.the<LibrariesForLibs>()

  withDeviceTestBuilder { sourceSetTreeName = "test" }
    .configure {
      animationsDisabled = true
      instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

      targetSdk { release(libs.versions.android.sdk.target.get().toInt()) }

      managedDevices { configureGradleManagedDevices() }

      action()
    }
}
