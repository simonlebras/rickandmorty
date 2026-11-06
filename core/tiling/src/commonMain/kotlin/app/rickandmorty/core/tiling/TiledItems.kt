package app.rickandmorty.core.tiling

import androidx.compose.runtime.Immutable
import com.tunjid.tiler.TiledList
import com.tunjid.tiler.emptyTiledList

@Immutable
internal data class TiledItems<out T>(
  val items: TiledList<Int, T> = emptyTiledList(),
  val loadStates: LoadStates = LoadStates.Initial,
  val revalidationError: Throwable? = null,
) {
  val isEmpty: Boolean
    get() = items.isEmpty()
}
