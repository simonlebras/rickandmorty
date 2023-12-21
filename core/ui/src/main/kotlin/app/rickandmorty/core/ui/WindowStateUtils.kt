package app.rickandmorty.core.ui

import android.graphics.Rect
import androidx.window.layout.FoldingFeature
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Information about the posture of the device.
 */
public sealed interface DevicePosture {
    public data object NormalPosture : DevicePosture

    public data class BookPosture(
        val hingePosition: Rect,
    ) : DevicePosture

    public data class Separating(
        val hingePosition: Rect,
        var orientation: FoldingFeature.Orientation,
    ) : DevicePosture
}

@OptIn(ExperimentalContracts::class)
public fun FoldingFeature?.isBookPosture(): Boolean {
    contract { returns(true) implies (this@isBookPosture != null) }
    return this != null &&
        state == FoldingFeature.State.HALF_OPENED &&
        orientation == FoldingFeature.Orientation.VERTICAL
}

@OptIn(ExperimentalContracts::class)
public fun FoldingFeature?.isSeparating(): Boolean {
    contract { returns(true) implies (this@isSeparating != null) }
    return this != null &&
        state == FoldingFeature.State.FLAT &&
        isSeparating
}
