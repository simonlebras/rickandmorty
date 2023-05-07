package app.rickandmorty.baselineprofile

import androidx.benchmark.macro.ExperimentalStableBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfileGenerator {
    @get:Rule
    val rule = BaselineProfileRule()

    @OptIn(ExperimentalStableBaselineProfilesApi::class)
    @Test
    fun generateBaselineProfile() = rule.collectStableBaselineProfile(
        packageName = PACKAGE_NAME,
        maxIterations = 8,
        stableIterations = 2,
    ) {
        pressHome()
        startActivityAndWait()
    }
}
