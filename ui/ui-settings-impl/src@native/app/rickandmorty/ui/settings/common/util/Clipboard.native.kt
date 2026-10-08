package app.rickandmorty.ui.settings.common.util

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard

@OptIn(ExperimentalComposeUiApi::class)
internal actual suspend fun Clipboard.setPlainText(label: String, text: String) {
  setClipEntry(ClipEntry.withPlainText(text))
}
