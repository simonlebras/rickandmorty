package app.rickandmorty.core.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
internal actual fun colorScheme(useDarkTheme: Boolean, useDynamicColor: Boolean): ColorScheme =
  when {
    useDynamicColor && useDarkTheme -> {
      dynamicDarkColorScheme(LocalContext.current)
    }

    useDynamicColor && !useDarkTheme -> {
      dynamicLightColorScheme(LocalContext.current)
    }

    useDarkTheme -> RamDarkColorScheme
    else -> RamLightColorScheme
  }

public actual fun isDynamicColorAvailable(): Boolean = true
