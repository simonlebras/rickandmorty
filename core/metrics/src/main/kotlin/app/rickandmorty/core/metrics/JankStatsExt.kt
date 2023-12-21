package app.rickandmorty.core.metrics

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.DisposableEffectResult
import androidx.compose.runtime.DisposableEffectScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalView
import androidx.metrics.performance.PerformanceMetricsState
import androidx.metrics.performance.PerformanceMetricsState.Holder
import kotlinx.coroutines.CoroutineScope

@Composable
public fun TrackJank(
    vararg keys: Any?,
    reportMetrics: suspend CoroutineScope.(Holder) -> Unit,
) {
    val metricsStateHolder = rememberMetricsStateHolder()
    LaunchedEffect(metricsStateHolder, *keys) {
        reportMetrics(metricsStateHolder)
    }
}

@Composable
public fun TrackDisposableJank(
    vararg keys: Any?,
    reportMetrics: DisposableEffectScope.(Holder) -> DisposableEffectResult,
) {
    val metricsStateHolder = rememberMetricsStateHolder()
    DisposableEffect(metricsStateHolder, *keys) {
        reportMetrics(metricsStateHolder)
    }
}

@Composable
public fun TrackScrollJank(
    scrollableState: ScrollableState,
    stateName: String,
) {
    TrackJank(scrollableState) { metricsStateHolder ->
        snapshotFlow { scrollableState.isScrollInProgress }.collect { isScrollInProgress ->
            metricsStateHolder.state?.let { state ->
                if (isScrollInProgress) {
                    state.putState(stateName, "Scrolling=true")
                } else {
                    state.removeState(stateName)
                }
            }
        }
    }
}

@Composable
private fun rememberMetricsStateHolder(): Holder {
    val localView = LocalView.current
    return remember(localView) {
        PerformanceMetricsState.getHolderForHierarchy(localView)
    }
}
