package app.rickandmorty.core.crashlytics

import android.content.Context
import app.rickandmorty.core.startup.Initializer
import co.touchlab.crashkios.crashlytics.enableCrashlytics
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics

internal class CrashlyticsInitializer(private val context: Context) : Initializer {
  override fun initialize() {
    // Collection is disabled in the manifest so that debug builds never report.
    if (FirebaseApp.getApps(context).isNotEmpty()) {
      FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = true
    }
    enableCrashlytics()
  }
}
