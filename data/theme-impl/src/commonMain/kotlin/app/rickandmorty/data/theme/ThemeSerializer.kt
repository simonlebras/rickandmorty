package app.rickandmorty.data.theme

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
public class ThemeSerializer : OkioSerializer<ThemeProto> {
  override val defaultValue: ThemeProto
    get() = ThemeProto()

  override suspend fun readFrom(source: BufferedSource): ThemeProto {
    return try {
      ProtoBuf.decodeFromByteArray(ThemeProto.serializer(), source.readByteArray())
    } catch (exception: IOException) {
      throw CorruptionException("Cannot read proto.", exception)
    }
  }

  override suspend fun writeTo(t: ThemeProto, sink: BufferedSink) {
    val bytes = ProtoBuf.encodeToByteArray(ThemeProto.serializer(), t)
    sink.write(bytes)
  }
}
