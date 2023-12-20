package app.rickandmorty.data.theme

import app.rickandmorty.data.model.NightMode

internal fun interface NightModeManager {
    fun setNightMode(nightMode: NightMode)
}
