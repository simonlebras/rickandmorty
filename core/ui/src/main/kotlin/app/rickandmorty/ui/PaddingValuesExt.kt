package app.rickandmorty.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.LayoutDirection

public operator fun PaddingValues.plus(padding: PaddingValues): PaddingValues = PaddingValues(
    start = calculateStartPadding(LayoutDirection.Ltr) +
        padding.calculateStartPadding(LayoutDirection.Ltr),
    top = calculateTopPadding() + padding.calculateTopPadding(),
    end = calculateEndPadding(LayoutDirection.Ltr) +
        padding.calculateEndPadding(LayoutDirection.Ltr),
    bottom = calculateBottomPadding() + padding.calculateBottomPadding(),
)
