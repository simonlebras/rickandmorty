package app.rickandmorty.inject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.createGraphFactory

@Inject
@ContributesBinding(AppScope::class)
class RamViewModelFactory(private val appGraph: AppGraph) : ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
    val viewModelGraph = createGraphFactory<ViewModelGraph.Factory>().create(appGraph, extras)

    val provider = viewModelGraph.viewModelProviders[modelClass.kotlin]
    requireNotNull(provider) { "Unknown model class $modelClass" }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    return modelClass.cast(provider())
  }
}
