package app.rickandmorty.data.theme

import android.app.Service
import android.content.Intent
import android.os.IBinder

internal class NightModeService : Service() {
  override fun onBind(intent: Intent): IBinder = throw UnsupportedOperationException()
}
