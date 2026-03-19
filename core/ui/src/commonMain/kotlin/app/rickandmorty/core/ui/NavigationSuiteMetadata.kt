package app.rickandmorty.core.ui

import androidx.navigation3.runtime.MetadataScope
import androidx.navigation3.runtime.NavMetadataKey

public data object ShowNavigationSuiteKey : NavMetadataKey<Boolean>

public fun MetadataScope.navigationSuite() {
  put(ShowNavigationSuiteKey, true)
}
