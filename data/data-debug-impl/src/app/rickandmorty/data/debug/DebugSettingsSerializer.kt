package app.rickandmorty.data.debug

import app.rickandmorty.core.datastore.ProtoBufSerializer

internal class DebugSettingsSerializer :
  ProtoBufSerializer<DebugSettingsProto>(
    serializer = DebugSettingsProto.serializer(),
    defaultValue = DebugSettingsProto(),
  )
