package app.rickandmorty.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.SdkSuppress
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
@SdkSuppress(minSdkVersion = 29)
class BaselineProfileGenerator {
  @get:Rule val rule = BaselineProfileRule()

  @Test
  fun generateBaselineProfile() =
    rule.collect(packageName = PACKAGE_NAME) {
      pressHome()
      startActivityAndWait()
    }
}
