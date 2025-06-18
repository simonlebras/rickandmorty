package app.rickandmorty.baselineprofile

import androidx.benchmark.ExperimentalBenchmarkConfigApi
import androidx.benchmark.ExperimentalConfig
import androidx.benchmark.StartupInsightsConfig
import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.benchmark.perfetto.ExperimentalPerfettoCaptureApi
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import kotlin.test.Test
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class StartupBenchmark {
  @get:Rule val rule = MacrobenchmarkRule()

  @Test fun startupCompilationNone() = benchmark(CompilationMode.None())

  @Test
  fun startupCompilationBaselineProfile() =
    benchmark(CompilationMode.Partial(BaselineProfileMode.Require))

  @OptIn(ExperimentalBenchmarkConfigApi::class, ExperimentalPerfettoCaptureApi::class)
  private fun benchmark(compilationMode: CompilationMode) {
    rule.measureRepeated(
      packageName = PACKAGE_NAME,
      metrics = listOf(StartupTimingMetric()),
      iterations = 10,
      experimentalConfig =
        ExperimentalConfig(startupInsightsConfig = StartupInsightsConfig(isEnabled = true)),
      compilationMode = compilationMode,
      startupMode = StartupMode.COLD,
      setupBlock = { pressHome() },
    ) {
      startActivityAndWait()
    }
  }
}
