package app.rickandmorty.theme.domain

import androidx.compose.runtime.Immutable

@Immutable
public data class Theme(
    val nightMode: NightMode,
    val useDynamicColor: Boolean,
)
