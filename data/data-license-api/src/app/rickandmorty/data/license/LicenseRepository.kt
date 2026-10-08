package app.rickandmorty.data.license

import kotlinx.collections.immutable.ImmutableList

public interface LicenseRepository {
  public suspend fun getLicenses(): ImmutableList<License>
}
