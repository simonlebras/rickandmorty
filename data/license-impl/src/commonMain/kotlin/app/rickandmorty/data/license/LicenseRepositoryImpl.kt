package app.rickandmorty.data.license

import app.rickandmorty.core.coroutines.inject.IODispatcher
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlin.coroutines.CoroutineContext
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.withContext

@ContributesBinding(AppScope::class)
public class LicenseRepositoryImpl(
  private val platformLicenseManager: PlatformLicenseManager,
  @IODispatcher private val ioDispatcher: CoroutineContext,
) : LicenseRepository {
  override suspend fun getLicenses(): ImmutableList<License> {
    return withContext(ioDispatcher) {
      platformLicenseManager.getLicenses().map { license -> license.toLicense() }.toImmutableList()
    }
  }
}

private fun LicenseJson.toLicense() = License(groupId, artifactId, version)
