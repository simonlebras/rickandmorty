package app.rickandmorty.core.strictmode

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import android.os.strictmode.UntaggedSocketViolation
import app.rickandmorty.core.base.unsafeLazy
import app.rickandmorty.core.startup.Initializer
import co.touchlab.kermit.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import java.util.concurrent.Executors

private const val TAG = "StrictMode"

@ContributesIntoSet(AppScope::class)
public class StrictModeInitializer : Initializer {
  private val penaltyListenerExecutor by unsafeLazy { Executors.newSingleThreadExecutor() }

  override fun initialize() {
    val threadPolicy =
      ThreadPolicy.Builder()
        .detectAll()
        .apply {
          penaltyListener(penaltyListenerExecutor) { violation ->
            Logger.withTag(TAG).w(violation) { "StrictMode ThreadPolicy violation" }
          }
        }
        .build()
    StrictMode.setThreadPolicy(threadPolicy)

    val vmPolicy =
      VmPolicy.Builder()
        .detectAll()
        .apply {
          penaltyListener(penaltyListenerExecutor) { violation ->
            when (violation) {
              is UntaggedSocketViolation -> {
                // Firebase and OkHttp don't tag sockets
                return@penaltyListener
              }
            }

            Logger.withTag(TAG).w(violation) { "StrictMode VmPolicy violation" }
          }
        }
        .build()
    StrictMode.setVmPolicy(vmPolicy)
  }
}
