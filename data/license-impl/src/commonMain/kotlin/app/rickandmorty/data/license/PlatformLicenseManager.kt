package app.rickandmorty.data.license

import kotlinx.collections.immutable.ImmutableList

internal interface PlatformLicenseManager {
  fun getLicenses(): ImmutableList<LicenseJson>
}
