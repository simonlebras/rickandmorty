package app.rickandmorty.data.license

import app.rickandmorty.core.coroutines.inject.IODispatcher
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.entity.License as AboutLibrariesLicense
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlin.coroutines.CoroutineContext
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.withContext

@ContributesBinding(AppScope::class)
internal class LicenseRepositoryImpl(
  private val licensesJsonSource: LicensesJsonSource,
  @IODispatcher private val ioDispatcher: CoroutineContext,
) : LicenseRepository {
  override suspend fun getLicenses(): ImmutableList<License> {
    return withContext(ioDispatcher) {
      Libs.Builder()
        .withJson(licensesJsonSource.getLicensesJson())
        .build()
        .libraries
        .map { library -> library.toLicense() }
        .toImmutableList()
    }
  }
}

private fun Library.toLicense() =
  License(
    uniqueId = uniqueId,
    name = name.ifBlank { uniqueId.substringAfterLast(':') },
    author =
      developers
        .mapNotNull { developer -> developer.name }
        .joinToString()
        .ifBlank { organization?.name.orEmpty() }
        .ifBlank { null },
    version = artifactVersion.orEmpty(),
    spdxIds =
      licenses
        .map { license -> license.spdxId.orEmpty().ifBlank { license.name } }
        .filter(String::isNotBlank)
        .toImmutableList(),
    url = licenses.firstNotNullOfOrNull(AboutLibrariesLicense::url) ?: website ?: scm?.url,
  )
