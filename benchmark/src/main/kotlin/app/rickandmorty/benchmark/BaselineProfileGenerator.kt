package app.rickandmorty.benchmark

import androidx.benchmark.macro.ExperimentalStableBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import org.junit.Rule
import org.junit.Test

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
        startActivityAndWait()
        device.waitForIdle()
    }
}
