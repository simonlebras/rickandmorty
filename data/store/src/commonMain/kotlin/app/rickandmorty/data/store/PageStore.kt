package app.rickandmorty.data.store

import app.rickandmorty.core.paging.Page
import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store6.core.ExperimentalStoreApi
import org.mobilenativefoundation.store6.core.Freshness
import org.mobilenativefoundation.store6.core.Store
import org.mobilenativefoundation.store6.core.StoreKey
import org.mobilenativefoundation.store6.core.StoreNamespace
import org.mobilenativefoundation.store6.core.store

public data class StoredPage<out E>(val entities: List<E>, val nextPage: Int?)

public abstract class PageStore<E : Any>(name: String, persistence: StorePersistence) {
  private val namespace = StoreNamespace(name)

  @OptIn(ExperimentalStoreApi::class)
  private val store: Store<PageKey, StoredPage<E>> = store {
    fetcher { key -> fetch(key.page) }
    persistence(
      persistence.sourceOfTruth(
        reader = { key -> read(key.page) },
        writer = { key, stored -> write(key.page, stored) },
        deleter = { key -> deletePage(key.page) },
        namespaceDeleter = { deleteAll() },
        allDeleter = { deleteAll() },
      ),
    )
    bookkeeper(persistence.bookkeeper)
  }

  protected abstract suspend fun fetch(page: Int): StoredPage<E>

  protected abstract fun read(page: Int): Flow<StoredPage<E>?>

  protected abstract suspend fun write(page: Int, stored: StoredPage<E>)

  protected abstract suspend fun deletePage(page: Int)

  protected abstract suspend fun deleteAll()

  public fun <T> page(page: Int, toItem: (E) -> T): Flow<Page<T>> =
    store
      .stream(PageKey(namespace, page), Freshness.CachedOrFetch)
      .toPages(items = { stored -> stored.entities.map(toItem) }, nextPage = { it.nextPage })

  public suspend fun invalidateAll() {
    store.invalidateNamespace(namespace)
  }

  private class PageKey(override val namespace: StoreNamespace, val page: Int) : StoreKey {
    override fun canonicalId(): String = page.toString()
  }
}
