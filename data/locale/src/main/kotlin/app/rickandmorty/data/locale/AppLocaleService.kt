package app.rickandmorty.data.locale

import android.app.Service
import android.content.Intent
import android.os.IBinder

internal class AppLocaleService : Service() {
    override fun onBind(intent: Intent): IBinder = throw UnsupportedOperationException()
}
