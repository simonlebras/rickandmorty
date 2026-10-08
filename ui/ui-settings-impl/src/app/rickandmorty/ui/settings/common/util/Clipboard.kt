package app.rickandmorty.ui.settings.common.util

import androidx.compose.ui.platform.Clipboard

internal expect suspend fun Clipboard.setPlainText(label: String, text: String)
