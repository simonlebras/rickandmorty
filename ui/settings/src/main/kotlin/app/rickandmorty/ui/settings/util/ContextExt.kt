package app.rickandmorty.ui.settings.util

import android.content.Context

internal val Context.versionName: String
    get() = packageManager.getPackageInfo(packageName, 0).versionName!!
