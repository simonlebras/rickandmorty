package app.rickandmorty.gradle.util

import java.util.Locale

internal fun String.capitalize(): String = replaceFirstChar {
  if (it.isLowerCase()) {
    it.titlecase(Locale.US)
  } else {
    it.toString()
  }
}
