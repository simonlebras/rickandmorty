package app.rickandmorty.data.theme

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber

@OptIn(ExperimentalSerializationApi::class)
@Serializable
public data class ThemeProto(
  @ProtoNumber(1) public val nightMode: NightModeProto = NightModeProto.UNSPECIFIED,
  @ProtoNumber(2) public val useDynamicColor: Boolean = false,
)
