package app.rickandmorty.data.license

import app.rickandmorty.data.model.License
import kotlinx.collections.immutable.ImmutableList

public interface LicenseRepository {
    public suspend fun getLicense() : ImmutableList<License>
}
