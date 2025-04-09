package app.rickandmorty.core.ui

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.core.util.Consumer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged

public fun ComponentActivity.isSystemInDarkTheme(): Flow<Boolean> =
  callbackFlow {
      trySend(resources.configuration.isSystemInDarkTheme)

      val listener = Consumer<Configuration> { config -> trySend(config.isSystemInDarkTheme) }

      addOnConfigurationChangedListener(listener)

      awaitClose { removeOnConfigurationChangedListener(listener) }
    }
    .distinctUntilChanged()
    .conflate()

private val Configuration.isSystemInDarkTheme
  get() = (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
