package app.rickandmorty.data.debug

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber

@OptIn(ExperimentalSerializationApi::class)
@Serializable
public data class DebugSettingsProto(
  @ProtoNumber(1) public val keylineOverlayEnabled: Boolean = false,
  @ProtoNumber(2) public val lookaheadDebuggingEnabled: Boolean = false,
)
