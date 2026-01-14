package app.rickandmorty.core.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

internal fun PaddingValues.minus(
  padding: PaddingValues,
  layoutDirection: LayoutDirection = LayoutDirection.Ltr,
): PaddingValues =
  PaddingValues(
    start =
      (calculateStartPadding(layoutDirection) - padding.calculateStartPadding(layoutDirection))
        .coerceAtLeast(0.dp),
    top = (calculateTopPadding() - padding.calculateTopPadding()).coerceAtLeast(0.dp),
    end =
      (calculateEndPadding(layoutDirection) - padding.calculateEndPadding(layoutDirection))
        .coerceAtLeast(0.dp),
    bottom = (calculateBottomPadding() - padding.calculateBottomPadding()).coerceAtLeast(0.dp),
  )
