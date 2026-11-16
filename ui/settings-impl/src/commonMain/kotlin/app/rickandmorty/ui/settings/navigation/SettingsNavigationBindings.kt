package app.rickandmorty.ui.settings.navigation

import androidx.navigation3.runtime.NavKey
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.inject.NavKeySerializerKey
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.IntoMap
import dev.zacsweers.metro.Provides
import kotlinx.serialization.KSerializer

@BindingContainer
@ContributesTo(UiScope::class)
internal object SettingsNavigationBindings {
  @Provides
  @IntoMap
  @NavKeySerializerKey(MainSettingsNavKey::class)
  fun provideMainSettingsSerializer(): KSerializer<out NavKey> = MainSettingsNavKey.serializer()

  @Provides
  @IntoMap
  @NavKeySerializerKey(LanguageSettingsNavKey::class)
  fun provideLanguageSettingsSerializer(): KSerializer<out NavKey> =
    LanguageSettingsNavKey.serializer()

  @Provides
  @IntoMap
  @NavKeySerializerKey(LicenseSettingsNavKey::class)
  fun provideLicenseSettingsSerializer(): KSerializer<out NavKey> =
    LicenseSettingsNavKey.serializer()
}
