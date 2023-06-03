package app.rickandmorty.utils

import android.graphics.Rect
import androidx.window.layout.FoldingFeature
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Information about the posture of the device.
 */
sealed interface DevicePosture {
    object NormalPosture : DevicePosture

    data class BookPosture(
        val hingePosition: Rect,
    ) : DevicePosture

    data class Separating(
        val hingePosition: Rect,
        var orientation: FoldingFeature.Orientation,
    ) : DevicePosture
}

/**
 * Different type of app navigation depending on device size and state.
 */
enum class NavigationType {
    BOTTOM_APP_BAR,
    NAVIGATION_RAIL,
    PERMANENT_NAVIGATION_DRAWER,
}

/**
 * Different position of navigation content inside navigation rail and navigation drawer
 * depending on device size and state.
 */
enum class NavigationContentPosition {
    TOP,
    CENTER,
}

/**
 * App content shown depending on device size and state.
 */
enum class ContentType {
    SINGLE_PANE,
    DUAL_PANE,
}

@OptIn(ExperimentalContracts::class)
fun isBookPosture(foldFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldFeature != null) }
    return foldFeature?.state == FoldingFeature.State.HALF_OPENED &&
        foldFeature.orientation == FoldingFeature.Orientation.VERTICAL
}

@OptIn(ExperimentalContracts::class)
fun isSeparating(foldFeature: FoldingFeature?): Boolean {
    contract { returns(true) implies (foldFeature != null) }
    return foldFeature?.state == FoldingFeature.State.FLAT && foldFeature.isSeparating
}
