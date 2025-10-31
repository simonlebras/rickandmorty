package app.rickandmorty.core.metro

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.GraphExtension
import dev.zacsweers.metro.Multibinds
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.Provides
import kotlin.reflect.KClass

public typealias ViewModelProviders = Map<KClass<out ViewModel>, Provider<ViewModel>>

@GraphExtension(ViewModelScope::class)
public interface ViewModelGraph {
  @Multibinds(allowEmpty = true) public val viewModelProviders: ViewModelProviders

  @Provides
  public fun provideSavedStateHandle(creationExtras: CreationExtras): SavedStateHandle =
    creationExtras.createSavedStateHandle()

  @GraphExtension.Factory
  @ContributesTo(AppScope::class)
  public fun interface Factory {
    public fun create(@Provides creationExtras: CreationExtras): ViewModelGraph
  }
}
