@file:OptIn(ExperimentalTextApi::class)

package app.rickandmorty.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import app.rickandmorty.designsystem.R

private val GmsFontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs,
)

private val RalewayFont = GoogleFont("Raleway")

private val RalewayFontFamily = createGoogleFontFamily(
    font = RalewayFont,
    variants = listOf(
        FontWeight.Light to FontStyle.Normal,
        FontWeight.Normal to FontStyle.Normal,
        FontWeight.Normal to FontStyle.Italic,
        FontWeight.Medium to FontStyle.Normal,
        FontWeight.Bold to FontStyle.Normal,
    ),
)

private fun createGoogleFontFamily(
    font: GoogleFont,
    variants: List<Pair<FontWeight, FontStyle>>,
): FontFamily = FontFamily(
    variants.map { (weight, style) ->
        Font(
            googleFont = font,
            fontProvider = GmsFontProvider,
            weight = weight,
            style = style,
        )
    },
)

internal val RamTypography by lazy {
    val default = Typography()
    Typography(
        displayLarge = default.displayLarge.copy(fontFamily = RalewayFontFamily),
        displayMedium = default.displayMedium.copy(fontFamily = RalewayFontFamily),
        displaySmall = default.displaySmall.copy(fontFamily = RalewayFontFamily),
        headlineLarge = default.headlineLarge.copy(fontFamily = RalewayFontFamily),
        headlineMedium = default.headlineMedium.copy(fontFamily = RalewayFontFamily),
        headlineSmall = default.headlineSmall.copy(fontFamily = RalewayFontFamily),
        titleLarge = default.titleLarge.copy(fontFamily = RalewayFontFamily),
        titleMedium = default.titleMedium.copy(fontFamily = RalewayFontFamily),
        titleSmall = default.titleSmall.copy(fontFamily = RalewayFontFamily),
        bodyLarge = default.bodyLarge.copy(fontFamily = RalewayFontFamily),
        bodyMedium = default.bodyMedium.copy(fontFamily = RalewayFontFamily),
        bodySmall = default.bodySmall.copy(fontFamily = RalewayFontFamily),
        labelLarge = default.labelLarge.copy(fontFamily = RalewayFontFamily),
        labelMedium = default.labelMedium.copy(fontFamily = RalewayFontFamily),
        labelSmall = default.labelSmall.copy(fontFamily = RalewayFontFamily),
    )
}
