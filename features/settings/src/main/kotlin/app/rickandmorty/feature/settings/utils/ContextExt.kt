package app.rickandmorty.feature.settings.utils

import android.content.Context

internal val Context.versionName: String
    get() = packageManager.getPackageInfo(packageName, 0).versionName
