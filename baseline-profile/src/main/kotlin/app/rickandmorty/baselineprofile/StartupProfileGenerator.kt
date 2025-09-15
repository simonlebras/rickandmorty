package app.rickandmorty.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.SdkSuppress
import kotlin.test.Test
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
@SdkSuppress(minSdkVersion = 29)
class StartupProfileGenerator {
  @get:Rule val baselineProfileRule = BaselineProfileRule()

  @Test
  fun generateStartupProfile() =
    baselineProfileRule.collect(packageName = PACKAGE_NAME, includeInStartupProfile = true) {
      startActivityAndWait()
    }
}
