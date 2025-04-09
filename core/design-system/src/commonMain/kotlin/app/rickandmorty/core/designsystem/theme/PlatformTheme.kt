package app.rickandmorty.core.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
internal expect fun colorScheme(useDarkTheme: Boolean, useDynamicColor: Boolean): ColorScheme

public expect fun isDynamicColorAvailable(): Boolean
