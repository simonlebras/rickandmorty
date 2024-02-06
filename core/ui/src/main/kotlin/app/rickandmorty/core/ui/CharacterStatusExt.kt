package app.rickandmorty.core.ui

import androidx.annotation.StringRes
import app.rickandmorty.core.ui.resources.R as UiR
import app.rickandmorty.data.model.Character.Status

@get:StringRes
public val Status.label: Int
    get() = when (this) {
        Status.Alive -> UiR.string.character_status_alive
        Status.Dead -> UiR.string.character_status_dead
        Status.Unknown -> UiR.string.character_status_unknown
    }
