package app.rickandmorty.core.strictmode

import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import android.os.strictmode.UntaggedSocketViolation
import app.rickandmorty.core.startup.Initializer
import co.touchlab.kermit.Logger
import dagger.Lazy
import java.util.concurrent.ExecutorService
import javax.inject.Inject
import se.ansman.dagger.auto.AutoBind

private const val TAG = "StrictMode"

@AutoBind
internal class StrictModeInitializer @Inject constructor(
    @StrictModeExecutor private val penaltyListenerExecutor: Lazy<ExecutorService>,
) : Initializer {
    override fun initialize() {
        val threadPolicy = ThreadPolicy.Builder()
            .detectAll()
            .apply {
                if (Build.VERSION.SDK_INT < 28) {
                    penaltyLog()
                } else {
                    penaltyListener(penaltyListenerExecutor.get()) { violation ->
                        Logger.withTag(TAG).w { violation.toString() }
                    }
                }
            }
            .build()
        StrictMode.setThreadPolicy(threadPolicy)

        val vmPolicy = VmPolicy.Builder()
            .detectAll()
            .apply {
                if (Build.VERSION.SDK_INT < 28) {
                    penaltyLog()
                } else {
                    penaltyListener(penaltyListenerExecutor.get()) { violation ->
                        when (violation) {
                            is UntaggedSocketViolation -> {
                                // Firebase and OkHttp don't tag sockets
                                return@penaltyListener
                            }
                        }

                        Logger.withTag(TAG).w { violation.toString() }
                    }
                }
            }
            .build()
        StrictMode.setVmPolicy(vmPolicy)
    }
}
