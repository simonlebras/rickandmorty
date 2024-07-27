package app.rickandmorty.data.license

import android.content.Context
import app.rickandmorty.core.metro.AppContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

@ContributesBinding(AppScope::class)
public class AndroidPlatformLicenseManager(@AppContext private val context: Context) :
  PlatformLicenseManager {
  @OptIn(ExperimentalSerializationApi::class)
  override fun getLicenses(): ImmutableList<LicenseJson> {
    return Json.decodeFromStream<List<LicenseJson>>(context.assets.open("licenses.json"))
      .toImmutableList()
  }
}
