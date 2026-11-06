package app.rickandmorty.data.store

import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store6.core.ExperimentalStoreApi
import org.mobilenativefoundation.store6.core.StoreKey
import org.mobilenativefoundation.store6.core.StoreNamespace
import org.mobilenativefoundation.store6.core.seam.Bookkeeper
import org.mobilenativefoundation.store6.core.seam.SourceOfTruth

@OptIn(ExperimentalStoreApi::class)
public interface StorePersistence {
  public val bookkeeper: Bookkeeper

  public fun <K : StoreKey, V : Any> sourceOfTruth(
    reader: (K) -> Flow<V?>,
    writer: suspend (K, V) -> Unit,
    deleter: suspend (K) -> Unit,
    namespaceDeleter: suspend (StoreNamespace) -> Unit,
    allDeleter: suspend () -> Unit,
  ): SourceOfTruth<K, V>
}
