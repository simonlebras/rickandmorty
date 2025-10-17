package app.rickandmorty.core.metro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlin.reflect.KClass
import kotlin.reflect.cast

@ContributesBinding(AppScope::class)
public class MetroViewModelFactory(private val viewModelGraphFactory: ViewModelGraph.Factory) :
  ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
    val viewModelGraph = viewModelGraphFactory.create(extras)

    val provider = viewModelGraph.viewModelProviders[modelClass]
    requireNotNull(provider) { "Unknown model class $modelClass" }

    return modelClass.cast(provider())
  }
}
