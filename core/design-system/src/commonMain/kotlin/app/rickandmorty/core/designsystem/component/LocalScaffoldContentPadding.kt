package app.rickandmorty.core.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Suppress("ComposeCompositionLocalUsage")
internal val LocalScaffoldContentPadding: ProvidableCompositionLocal<PaddingValues> =
  staticCompositionLocalOf {
    PaddingValues(0.dp)
  }
