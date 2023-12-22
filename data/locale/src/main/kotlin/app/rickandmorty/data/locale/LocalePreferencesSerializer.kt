package app.rickandmorty.data.locale

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.IOException
import androidx.datastore.core.Serializer
import app.rickandmorty.data.locale.proto.LocalePreferences
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

internal class LocalePreferencesSerializer @Inject constructor() : Serializer<LocalePreferences> {
    override val defaultValue: LocalePreferences
        get() = LocalePreferences()

    override suspend fun readFrom(input: InputStream): LocalePreferences {
        return try {
            LocalePreferences.ADAPTER.decode(input)
        } catch (exception: IOException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: LocalePreferences, output: OutputStream) {
        LocalePreferences.ADAPTER.encode(output, t)
    }
}
