package app.rickandmorty.core.tiling

import androidx.compose.runtime.Stable
import app.rickandmorty.core.paging.FIRST_PAGE_KEY
import com.tunjid.snapshottable.SnapshotSpec
import com.tunjid.snapshottable.Snapshottable
import com.tunjid.tiler.TiledList
import com.tunjid.tiler.emptyTiledList

@Stable
public interface TilingState<T> {
  public val tilingData: Data<T>

  @Stable
  @Snapshottable
  public interface Data<T> {
    @SnapshotSpec
    public data class Immutable<T>(
      val items: TiledList<Int, T> = emptyTiledList(),
      val loadStates: LoadStates = LoadStates.Initial,
      val currentPage: Int = FIRST_PAGE_KEY,
    ) : Data<T>
  }

  public sealed interface Action {
    public data class LoadAround(val page: Int) : Action

    public data object Retry : Action

    public data object Refresh : Action
  }
}

public val TilingState<*>.isSettled: Boolean
  get() = tilingData.items.isNotEmpty() || tilingData.loadStates.isIdle

public sealed interface TilingLoadError {
  public val error: Throwable

  public class Refresh(override val error: Throwable) : TilingLoadError

  public class Revalidation(override val error: Throwable) : TilingLoadError
}

public fun <T> tilingData(currentPage: Int = FIRST_PAGE_KEY): TilingState.Data<T> =
  TilingState.Data.Immutable<T>(currentPage = currentPage).toSnapshotMutable()

internal fun <T> TilingState.Data<T>.requireMutable(): TilingState.Data.Mutable<T> {
  check(this is TilingState.Data.Mutable<T>) { "Tiling state must be mutable" }
  return this
}
