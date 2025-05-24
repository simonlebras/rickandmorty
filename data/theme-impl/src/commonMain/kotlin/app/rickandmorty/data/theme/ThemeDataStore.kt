package app.rickandmorty.data.theme

import androidx.datastore.core.DataStore
import app.rickandmorty.data.theme.proto.ThemePreferences
import kotlin.jvm.JvmInline

@JvmInline public value class ThemeDataStore(internal val value: DataStore<ThemePreferences>)
