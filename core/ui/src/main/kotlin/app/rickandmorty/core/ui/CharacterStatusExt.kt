package app.rickandmorty.core.ui

import androidx.annotation.StringRes
import app.rickandmorty.core.l10n.R as L10nR
import app.rickandmorty.data.model.Character.Status

@get:StringRes
public val Status.label: Int
    get() = when (this) {
        Status.Alive -> L10nR.string.character_status_alive
        Status.Dead -> L10nR.string.character_status_dead
        Status.Unknown -> L10nR.string.character_status_unknown
    }
