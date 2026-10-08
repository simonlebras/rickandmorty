package app.rickandmorty.data.theme

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber

@OptIn(ExperimentalSerializationApi::class)
@Serializable
public enum class NightModeProto {
  @ProtoNumber(0) UNSPECIFIED,
  @ProtoNumber(1) AUTO_BATTERY,
  @ProtoNumber(2) FOLLOW_SYSTEM,
  @ProtoNumber(3) LIGHT,
  @ProtoNumber(4) DARK,
}
