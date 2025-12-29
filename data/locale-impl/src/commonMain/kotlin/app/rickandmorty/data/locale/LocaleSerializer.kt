package app.rickandmorty.data.locale

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.okio.OkioSerializer
import dev.zacsweers.metro.Inject
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf
import okio.BufferedSink
import okio.BufferedSource
import okio.IOException

@OptIn(ExperimentalSerializationApi::class)
@Inject
public class LocaleSerializer : OkioSerializer<LocaleProto> {
  override val defaultValue: LocaleProto
    get() = LocaleProto()

  override suspend fun readFrom(source: BufferedSource): LocaleProto {
    return try {
      ProtoBuf.decodeFromByteArray(LocaleProto.serializer(), source.readByteArray())
    } catch (exception: IOException) {
      throw CorruptionException("Cannot read proto.", exception)
    }
  }

  override suspend fun writeTo(t: LocaleProto, sink: BufferedSink) {
    val bytes = ProtoBuf.encodeToByteArray(LocaleProto.serializer(), t)
    sink.write(bytes)
  }
}
