package app.rickandmorty.data.locale

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import app.rickandmorty.core.coroutines.inject.ApplicationScope
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ContributesBinding(AppScope::class)
internal class LocaleRepositoryImpl(
  private val platformLocaleManager: PlatformLocaleManager,
  @ApplicationScope private val applicationScope: CoroutineScope,
) : LocaleRepository {
  private val _appLocale = mutableStateOf<AppLocale>(AppLocale.System)
  override val appLocale: State<AppLocale> = _appLocale

  init {
    applicationScope.launch {
      platformLocaleManager.getAppLocale().collect { _appLocale.value = it }
    }
  }

  override fun setAppLocale(appLocale: AppLocale) {
    applicationScope.launch { platformLocaleManager.setAppLocale(appLocale) }
  }

  override suspend fun getAvailableAppLocales(): ImmutableList<AppLocale> =
    persistentListOf<AppLocale>(AppLocale.System)
      .addingAll(platformLocaleManager.getAvailableAppLocales())
}
