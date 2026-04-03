package app.rickandmorty.core.json.inject

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.serialization.json.Json

@ContributesTo(AppScope::class)
public interface JsonProvider {
  public companion object {
    @Provides
    @SingleIn(AppScope::class)
    public fun provideJson(): Json = Json {
      explicitNulls = false
      ignoreUnknownKeys = true
    }
  }
}
