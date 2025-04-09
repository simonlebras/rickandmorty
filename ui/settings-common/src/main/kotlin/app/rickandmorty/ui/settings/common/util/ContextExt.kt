package app.rickandmorty.ui.settings.common.util

import android.content.Context

public val Context.versionName: String
  get() = packageManager.getPackageInfo(packageName, 0).versionName!!
