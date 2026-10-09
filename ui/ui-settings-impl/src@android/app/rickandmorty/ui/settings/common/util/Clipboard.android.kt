package app.rickandmorty.ui.settings.common.util

import android.content.ClipData
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.toClipEntry

internal actual suspend fun Clipboard.setPlainText(label: String, text: String) {
  setClipEntry(ClipData.newPlainText(label, text).toClipEntry())
}
