package app.rickandmorty.data.theme

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
public data class Theme(val nightMode: NightMode, val useDynamicColor: Boolean)
