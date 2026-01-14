package app.rickandmorty.core.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

@Suppress("ComposeCompositionLocalUsage")
internal val LocalScaffoldContentPadding: ProvidableCompositionLocal<PaddingValues> =
  staticCompositionLocalOf {
    PaddingValues.Zero
  }
