package app.rickandmorty.ui.settings.license

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import app.rickandmorty.core.coroutines.viewModelCoroutineScope
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.data.license.LicenseRepository
import com.tunjid.mutator.coroutines.SuspendingStateMutator
import com.tunjid.mutator.coroutines.launchedCollect
import com.tunjid.mutator.coroutines.suspendingStateMutator
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactoryKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow

@Stable public interface LicenseSettingsStateHolder : SuspendingStateMutator<LicenseSettingsUiState>

@AssistedFactory
@ContributesIntoMap(UiScope::class)
@ViewModelAssistedFactoryKey(LicenseSettingsViewModel::class)
public fun interface LicenseSettingsViewModelInitializer : ViewModelAssistedFactory {
  override fun create(extras: CreationExtras): LicenseSettingsViewModel =
    invoke(scope = viewModelCoroutineScope())

  public fun invoke(@Assisted scope: CoroutineScope): LicenseSettingsViewModel
}

public class LicenseSettingsViewModel(
  mutator: SuspendingStateMutator<LicenseSettingsUiState>,
  scope: CoroutineScope,
) :
  ViewModel(viewModelScope = scope),
  LicenseSettingsStateHolder,
  SuspendingStateMutator<LicenseSettingsUiState> by mutator {
  @AssistedInject
  public constructor(
    licenseRepository: LicenseRepository,
    @Assisted scope: CoroutineScope,
  ) : this(
    mutator =
      scope.suspendingStateMutator(
        state = LicenseSettingsUiState().toSnapshotMutable(),
        producer = { state ->
          launchLoadLicensesMutations(state = state, licenseRepository = licenseRepository)
        },
      ),
    scope = scope,
  )
}

context(productionScope: CoroutineScope)
private fun launchLoadLicensesMutations(
  state: LicenseSettingsUiState.Mutable,
  licenseRepository: LicenseRepository,
) = flow {
  if (state.licenses == null) emit(licenseRepository.getLicenses())
}
  .launchedCollect { licenses -> state.licenses = licenses }
