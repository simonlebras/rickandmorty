package app.rickandmorty.data.locale

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber

@OptIn(ExperimentalSerializationApi::class)
@Serializable
public data class LocaleProto(@ProtoNumber(1) public val appLocale: String? = null)
