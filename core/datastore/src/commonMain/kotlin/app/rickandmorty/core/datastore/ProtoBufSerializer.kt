package app.rickandmorty.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.okio.OkioSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.protobuf.ProtoBuf
import okio.BufferedSink
import okio.BufferedSource
import okio.IOException

@OptIn(ExperimentalSerializationApi::class)
public abstract class ProtoBufSerializer<T>(
  private val serializer: KSerializer<T>,
  override val defaultValue: T,
) : OkioSerializer<T> {
  override suspend fun readFrom(source: BufferedSource): T {
    return try {
      ProtoBuf.decodeFromByteArray(serializer, source.readByteArray())
    } catch (exception: IOException) {
      throw CorruptionException("Cannot read protobuf.", exception)
    }
  }

  override suspend fun writeTo(t: T, sink: BufferedSink) {
    val bytes = ProtoBuf.encodeToByteArray(serializer, t)
    sink.write(bytes)
  }
}
