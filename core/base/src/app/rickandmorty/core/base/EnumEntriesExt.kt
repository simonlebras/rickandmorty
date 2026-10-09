package app.rickandmorty.core.base

import kotlin.enums.EnumEntries

public fun <T : Enum<T>> EnumEntries<T>.findByNameOrElse(name: String, defaultValue: T): T {
  return firstOrNull { it.name.equals(name, ignoreCase = true) } ?: defaultValue
}
