package app.rickandmorty.data.database

import app.rickandmorty.data.store.StorePersistence
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store6.core.ExperimentalStoreApi
import org.mobilenativefoundation.store6.core.StoreKey
import org.mobilenativefoundation.store6.core.StoreNamespace
import org.mobilenativefoundation.store6.core.seam.Bookkeeper
import org.mobilenativefoundation.store6.core.seam.SourceOfTruth
import org.mobilenativefoundation.store6.room.RoomBookkeeper
import org.mobilenativefoundation.store6.room.RoomSourceOfTruth

@OptIn(ExperimentalStoreApi::class)
@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
internal class RoomStorePersistence(private val database: RamDatabase) : StorePersistence {
  override val bookkeeper: Bookkeeper = RoomBookkeeper(database, database.store6BookkeeperDao())

  override fun <K : StoreKey, V : Any> sourceOfTruth(
    reader: (K) -> Flow<V?>,
    writer: suspend (K, V) -> Unit,
    deleter: suspend (K) -> Unit,
    namespaceDeleter: suspend (StoreNamespace) -> Unit,
    allDeleter: suspend () -> Unit,
  ): SourceOfTruth<K, V> =
    RoomSourceOfTruth(
      database = database,
      rowReader = reader,
      rowWriter = writer,
      rowDeleter = deleter,
      namespaceDeleter = namespaceDeleter,
      allDeleter = allDeleter,
    )
}
