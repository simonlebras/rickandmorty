package app.rickandmorty.core.designsystem.component.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

@Immutable
internal data class PaddingValuesInsets(private val padding: PaddingValues) : WindowInsets {
    override fun getLeft(density: Density, layoutDirection: LayoutDirection): Int = with(density) {
        padding.calculateLeftPadding(layoutDirection).roundToPx()
    }

    override fun getTop(density: Density): Int = with(density) {
        padding.calculateTopPadding().roundToPx()
    }

    override fun getRight(density: Density, layoutDirection: LayoutDirection): Int = with(density) {
        padding.calculateRightPadding(layoutDirection).roundToPx()
    }

    override fun getBottom(density: Density): Int = with(density) {
        padding.calculateBottomPadding().roundToPx()
    }
}
