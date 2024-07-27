package app.rickandmorty.data.license

import kotlinx.collections.immutable.ImmutableList

public interface PlatformLicenseManager {
  public fun getLicenses(): ImmutableList<LicenseJson>
}
