package app.rickandmorty.ui

import androidx.compose.runtime.Stable
import app.rickandmorty.data.theme.Theme

@Stable data class MainUiState(val theme: Theme? = null)

val MainUiState.isLoading: Boolean
  get() = theme == null
