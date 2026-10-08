package app.rickandmorty.data.theme

import app.rickandmorty.core.datastore.ProtoBufSerializer

internal class ThemeSerializer :
  ProtoBufSerializer<ThemeProto>(serializer = ThemeProto.serializer(), defaultValue = ThemeProto())
