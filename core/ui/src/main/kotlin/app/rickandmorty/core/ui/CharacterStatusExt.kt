package app.rickandmorty.core.ui

import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.character_status_alive
import app.rickandmorty.core.l10n.resources.character_status_dead
import app.rickandmorty.core.l10n.resources.character_status_unknown
import app.rickandmorty.data.model.Character.Status
import org.jetbrains.compose.resources.StringResource

public val Status.label: StringResource
    get() = when (this) {
        Status.Alive -> L10nRes.string.character_status_alive
        Status.Dead -> L10nRes.string.character_status_dead
        Status.Unknown -> L10nRes.string.character_status_unknown
    }
