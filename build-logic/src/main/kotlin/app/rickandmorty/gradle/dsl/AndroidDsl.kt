package app.rickandmorty.gradle.dsl

import com.android.build.api.dsl.KotlinMultiplatformAndroidDeviceTest
import com.android.build.api.dsl.KotlinMultiplatformAndroidHostTest
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget

public fun KotlinMultiplatformAndroidLibraryTarget.hostTest(
  configuration: KotlinMultiplatformAndroidHostTest.() -> Unit = {}
) {
  withHostTest {
    isIncludeAndroidResources = true

    configuration()
  }
}

public fun KotlinMultiplatformAndroidLibraryTarget.deviceTest(
  configuration: KotlinMultiplatformAndroidDeviceTest.() -> Unit = {}
) {
  withDeviceTestBuilder { sourceSetTreeName = "test" }
    .configure {
      animationsDisabled = true
      instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

      configuration()
    }
}
