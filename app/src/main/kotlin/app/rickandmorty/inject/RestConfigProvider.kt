package app.rickandmorty.inject

import app.rickandmorty.data.rest.RestConfig
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@BindingContainer
@ContributesTo(AppScope::class)
object RestConfigProvider {
  @Provides
  fun provideRestConfig(): RestConfig = RestConfig(baseUrl = "https://rickandmortyapi.com/api/")
}
