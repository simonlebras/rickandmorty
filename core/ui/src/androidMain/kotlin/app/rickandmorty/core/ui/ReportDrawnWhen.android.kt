package app.rickandmorty.core.ui

import androidx.activity.compose.ReportDrawnWhen as PlatformReportDrawnWhen
import androidx.compose.runtime.Composable

@Composable
public actual fun ReportDrawnWhen(predicate: () -> Boolean) {
  PlatformReportDrawnWhen(predicate)
}
