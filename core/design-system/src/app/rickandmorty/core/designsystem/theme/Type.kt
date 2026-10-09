package app.rickandmorty.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import app.rickandmorty.core.designsystem.resources.Res
import app.rickandmorty.core.designsystem.resources.dm_sans_bold
import app.rickandmorty.core.designsystem.resources.dm_sans_medium
import app.rickandmorty.core.designsystem.resources.dm_sans_regular
import org.jetbrains.compose.resources.Font

private val DmSansFontFamily: FontFamily
  @Composable
  get() =
    FontFamily(
      Font(resource = Res.font.dm_sans_regular, weight = FontWeight.Normal),
      Font(resource = Res.font.dm_sans_medium, weight = FontWeight.Medium),
      Font(resource = Res.font.dm_sans_bold, weight = FontWeight.Bold),
    )

internal val RamTypography: Typography
  @Composable
  get() {
    val default = Typography()
    val dmSansFontFamily = DmSansFontFamily
    return Typography(
      displayLarge = default.displayLarge.copy(fontFamily = dmSansFontFamily),
      displayMedium = default.displayMedium.copy(fontFamily = dmSansFontFamily),
      displaySmall = default.displaySmall.copy(fontFamily = dmSansFontFamily),
      headlineLarge = default.headlineLarge.copy(fontFamily = dmSansFontFamily),
      headlineMedium = default.headlineMedium.copy(fontFamily = dmSansFontFamily),
      headlineSmall = default.headlineSmall.copy(fontFamily = dmSansFontFamily),
      titleLarge = default.titleLarge.copy(fontFamily = dmSansFontFamily),
      titleMedium = default.titleMedium.copy(fontFamily = dmSansFontFamily),
      titleSmall = default.titleSmall.copy(fontFamily = dmSansFontFamily),
      bodyLarge = default.bodyLarge.copy(fontFamily = dmSansFontFamily),
      bodyMedium = default.bodyMedium.copy(fontFamily = dmSansFontFamily),
      bodySmall = default.bodySmall.copy(fontFamily = dmSansFontFamily),
      labelLarge = default.labelLarge.copy(fontFamily = dmSansFontFamily),
      labelMedium = default.labelMedium.copy(fontFamily = dmSansFontFamily),
      labelSmall = default.labelSmall.copy(fontFamily = dmSansFontFamily),
    )
  }
