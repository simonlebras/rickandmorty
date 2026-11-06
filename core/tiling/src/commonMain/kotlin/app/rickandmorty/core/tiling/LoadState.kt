package app.rickandmorty.core.tiling

import androidx.compose.runtime.Immutable

@Immutable
public sealed interface LoadState {
  public data object Loading : LoadState

  public data object NotLoading : LoadState

  public data class Error(val error: Throwable) : LoadState
}
