package app.rickandmorty.core.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
internal actual fun colorScheme(
    useDarkTheme: Boolean,
    useDynamicColor: Boolean,
): ColorScheme = when {
    useDarkTheme -> RamDarkColorScheme
    else -> RamLightColorScheme
}

public actual fun isDynamicColorAvailable(): Boolean = false
