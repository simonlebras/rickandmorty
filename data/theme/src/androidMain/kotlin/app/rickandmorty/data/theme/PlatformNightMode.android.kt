package app.rickandmorty.data.theme

import android.os.Build
import app.rickandmorty.data.model.NightMode

internal actual val defaultNightMode =
  if (Build.VERSION.SDK_INT >= 29) {
    NightMode.FollowSystem
  } else {
    NightMode.AutoBattery
  }
