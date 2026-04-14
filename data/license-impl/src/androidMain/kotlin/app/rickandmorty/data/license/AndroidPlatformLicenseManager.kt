package app.rickandmorty.data.license

import android.content.Context
import app.rickandmorty.core.metro.AppContext
import app.rickandmorty.data.license.inject.LicensesAssetPath
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

@ContributesBinding(AppScope::class)
internal class AndroidPlatformLicenseManager(
  @param:AppContext private val context: Context,
  @param:LicensesAssetPath private val licensesAssetPath: String,
  private val json: Json,
) : PlatformLicenseManager {
  @OptIn(ExperimentalSerializationApi::class)
  override fun getLicenses(): ImmutableList<LicenseJson> {
    return json
      .decodeFromStream<List<LicenseJson>>(context.assets.open(licensesAssetPath))
      .toImmutableList()
  }
}
