package app.rickandmorty.core.designsystem.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
internal actual fun colorScheme(
    useDarkTheme: Boolean,
    useDynamicColor: Boolean,
): ColorScheme = when {
    Build.VERSION.SDK_INT >= 31 && useDynamicColor && useDarkTheme -> {
        dynamicDarkColorScheme(LocalContext.current)
    }

    Build.VERSION.SDK_INT >= 31 && useDynamicColor && !useDarkTheme -> {
        dynamicLightColorScheme(LocalContext.current)
    }

    useDarkTheme -> RamDarkColorScheme
    else -> RamLightColorScheme
}

@ChecksSdkIntAtLeast(api = 31)
public actual fun isDynamicColorAvailable(): Boolean = Build.VERSION.SDK_INT >= 31
