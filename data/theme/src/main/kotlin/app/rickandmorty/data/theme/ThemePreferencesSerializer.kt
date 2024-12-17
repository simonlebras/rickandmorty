package app.rickandmorty.data.theme

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.IOException
import androidx.datastore.core.Serializer
import app.rickandmorty.data.theme.proto.NightMode
import app.rickandmorty.data.theme.proto.ThemePreferences
import java.io.InputStream
import java.io.OutputStream

internal class ThemePreferencesSerializer : Serializer<ThemePreferences> {
    override val defaultValue: ThemePreferences
        get() = ThemePreferences(
            night_mode = NightMode.UNSPECIFIED,
            use_dynamic_color = true,
        )

    override suspend fun readFrom(input: InputStream): ThemePreferences = try {
        ThemePreferences.ADAPTER.decode(input)
    } catch (exception: IOException) {
        throw CorruptionException("Cannot read proto.", exception)
    }

    override suspend fun writeTo(t: ThemePreferences, output: OutputStream) {
        ThemePreferences.ADAPTER.encode(output, t)
    }
}
