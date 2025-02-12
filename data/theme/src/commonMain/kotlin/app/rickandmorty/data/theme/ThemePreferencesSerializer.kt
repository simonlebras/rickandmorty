package app.rickandmorty.data.theme

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.IOException
import androidx.datastore.core.okio.OkioSerializer
import app.rickandmorty.data.theme.proto.NightMode
import app.rickandmorty.data.theme.proto.ThemePreferences
import me.tatarka.inject.annotations.Inject
import okio.BufferedSink
import okio.BufferedSource

@Inject
public class ThemePreferencesSerializer : OkioSerializer<ThemePreferences> {
    override val defaultValue: ThemePreferences
        get() = ThemePreferences(
            night_mode = NightMode.UNSPECIFIED,
            use_dynamic_color = true,
        )

    override suspend fun readFrom(source: BufferedSource): ThemePreferences = try {
        ThemePreferences.ADAPTER.decode(source)
    } catch (exception: IOException) {
        throw CorruptionException("Cannot read proto.", exception)
    }

    override suspend fun writeTo(
        t: ThemePreferences,
        sink: BufferedSink,
    ) {
        sink.write(t.encode())
    }
}
