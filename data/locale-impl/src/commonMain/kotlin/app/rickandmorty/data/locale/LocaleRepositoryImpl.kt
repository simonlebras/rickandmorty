package app.rickandmorty.data.locale

import app.rickandmorty.core.coroutines.inject.ApplicationScope
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@ContributesBinding(AppScope::class)
internal class LocaleRepositoryImpl(
  private val platformLocaleManager: PlatformLocaleManager,
  @ApplicationScope private val applicationScope: CoroutineScope,
) : LocaleRepository {
  override fun getAppLocale(): Flow<AppLocale> {
    return platformLocaleManager.getAppLocale()
  }

  override suspend fun setAppLocale(appLocale: AppLocale) {
    applicationScope.launch { platformLocaleManager.setAppLocale(appLocale) }.join()
  }

  override suspend fun getAvailableAppLocales(): ImmutableList<AppLocale> {
    return persistentListOf<AppLocale>(AppLocale.System)
      .addingAll(platformLocaleManager.getAvailableAppLocales())
  }
}
