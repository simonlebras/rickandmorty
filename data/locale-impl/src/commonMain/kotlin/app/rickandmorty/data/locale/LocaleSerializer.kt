package app.rickandmorty.data.locale

import app.rickandmorty.core.datastore.ProtoBufSerializer
import dev.zacsweers.metro.Inject

@Inject
public class LocaleSerializer :
  ProtoBufSerializer<LocaleProto>(
    serializer = LocaleProto.serializer(),
    defaultValue = LocaleProto(),
  )
