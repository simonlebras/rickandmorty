package app.rickandmorty.data.theme

import app.rickandmorty.core.datastore.ProtoBufSerializer
import dev.zacsweers.metro.Inject

@Inject
public class ThemeSerializer :
  ProtoBufSerializer<ThemeProto>(serializer = ThemeProto.serializer(), defaultValue = ThemeProto())
