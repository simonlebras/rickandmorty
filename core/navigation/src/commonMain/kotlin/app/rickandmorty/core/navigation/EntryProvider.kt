package app.rickandmorty.core.navigation

import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey

public typealias EntryProvider = (NavKey) -> NavEntry<NavKey>
