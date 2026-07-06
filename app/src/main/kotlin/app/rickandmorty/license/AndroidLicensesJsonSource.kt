package app.rickandmorty.license

import android.content.Context
import app.rickandmorty.R
import app.rickandmorty.core.metro.AppContext
import app.rickandmorty.data.license.LicensesJsonSource
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

@ContributesBinding(AppScope::class)
class AndroidLicensesJsonSource(@AppContext private val context: Context) : LicensesJsonSource {
  override fun getLicensesJson(): String =
    context.resources.openRawResource(R.raw.aboutlibraries).reader().use { it.readText() }
}
