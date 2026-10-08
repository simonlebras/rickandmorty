package app.rickandmorty.core.ui

import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.character_status_alive
import app.rickandmorty.core.l10n.resources.character_status_dead
import app.rickandmorty.core.l10n.resources.character_status_unknown
import app.rickandmorty.data.character.Character.Status
import org.jetbrains.compose.resources.StringResource

public val Status.label: StringResource
  get() =
    when (this) {
      Alive -> L10nRes.string.character_status_alive
      Dead -> L10nRes.string.character_status_dead
      Unknown -> L10nRes.string.character_status_unknown
    }
