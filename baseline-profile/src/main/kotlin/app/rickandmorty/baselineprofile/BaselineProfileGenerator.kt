package app.rickandmorty.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import kotlin.test.Test
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfileGenerator {
  @get:Rule val rule = BaselineProfileRule()

  @Test
  fun generateBaselineProfile() =
    rule.collect(packageName = PACKAGE_NAME) {
      pressHome()
      startActivityAndWait()
    }
}
