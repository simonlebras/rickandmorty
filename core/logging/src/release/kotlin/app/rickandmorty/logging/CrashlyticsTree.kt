package app.rickandmorty.logging

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject
import timber.log.Timber

internal class CrashlyticsTree @Inject constructor(
    private val firebaseCrashlytics: FirebaseCrashlytics,
) : Timber.Tree() {
    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority >= Log.INFO
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        try {
            firebaseCrashlytics.log(message)
        } catch (_: IllegalStateException) {
            // Firebase is likely not setup in this project.
        }
    }
}
