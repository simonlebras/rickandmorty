package app.rickandmorty.core.tiling

import androidx.compose.runtime.Immutable

@Immutable
public data class LoadStates(
  val refresh: LoadState,
  val prepend: LoadState,
  val append: LoadState,
) {
  public val isIdle: Boolean
    get() =
      refresh == LoadState.NotLoading &&
        prepend == LoadState.NotLoading &&
        append == LoadState.NotLoading

  companion {
    public val Initial: LoadStates =
      LoadStates(
        refresh = LoadState.Loading,
        prepend = LoadState.NotLoading,
        append = LoadState.NotLoading,
      )
  }
}
