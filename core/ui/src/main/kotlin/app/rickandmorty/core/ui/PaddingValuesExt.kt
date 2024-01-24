package app.rickandmorty.core.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

public fun PaddingValues.plus(
    padding: PaddingValues,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
): PaddingValues = PaddingValues(
    start = calculateStartPadding(layoutDirection) + padding.calculateStartPadding(layoutDirection),
    top = calculateTopPadding() + padding.calculateTopPadding(),
    end = calculateEndPadding(layoutDirection) + padding.calculateEndPadding(layoutDirection),
    bottom = calculateBottomPadding() + padding.calculateBottomPadding(),
)

public fun PaddingValues.minus(
    padding: PaddingValues,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
): PaddingValues = PaddingValues(
    start = (calculateStartPadding(layoutDirection) - padding.calculateStartPadding(layoutDirection))
        .coerceAtLeast(0.dp),
    top = (calculateTopPadding() - padding.calculateTopPadding()).coerceAtLeast(0.dp),
    end = (calculateEndPadding(layoutDirection) - padding.calculateEndPadding(layoutDirection))
        .coerceAtLeast(0.dp),
    bottom = (calculateBottomPadding() - padding.calculateBottomPadding()).coerceAtLeast(0.dp),
)
