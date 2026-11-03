package app.rickandmorty.core.appstate.inject

import androidx.appstate.datastore.syncToDataStore
import androidx.appstate.statestore.StateStore
import app.rickandmorty.core.coroutines.inject.ApplicationScope
import app.rickandmorty.core.datastore.FilePathProducer
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@BindingContainer
@ContributesTo(AppScope::class)
public object StateStoreProvider {
  @Provides
  @SingleIn(AppScope::class)
  public fun stateStore(
    @ApplicationScope scope: CoroutineScope,
    filePathProducer: FilePathProducer,
  ): StateStore =
    StateStore().also { store ->
      scope.launch {
        store.syncToDataStore(
          path = filePathProducer("statestore.json").toString(),
          coroutineScope = scope,
        )
      }
    }
}
