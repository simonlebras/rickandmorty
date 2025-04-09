package app.rickandmorty.ui.settings.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

public fun LazyListScope.loader() {
  item(key = "loader", contentType = SettingsContentType.LOADER) {
    Box(modifier = Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
      CircularProgressIndicator()
    }
  }
}
