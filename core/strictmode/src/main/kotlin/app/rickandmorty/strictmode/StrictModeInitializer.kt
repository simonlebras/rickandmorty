package app.rickandmorty.strictmode

import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import android.os.strictmode.UntaggedSocketViolation
import app.rickandmorty.hilt.HiltLazy
import app.rickandmorty.logging.Logger
import app.rickandmorty.startup.Initializer
import java.util.concurrent.ExecutorService
import javax.inject.Inject

private const val TAG = "StrictMode"

internal class StrictModeInitializer @Inject constructor(
    private val logger: Logger,
    @StrictModeExecutor private val penaltyListenerExecutor: HiltLazy<ExecutorService>,
) : Initializer {
    override fun initialize() {
        val threadPolicy = ThreadPolicy.Builder()
            .detectAll()
            .apply {
                if (Build.VERSION.SDK_INT < 28) {
                    penaltyLog()
                } else {
                    penaltyListener(penaltyListenerExecutor.get()) { violation ->
                        logger.tag(TAG).w(violation)
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

                        logger.tag(TAG).w(violation)
                    }
                }
            }
            .build()
        StrictMode.setVmPolicy(vmPolicy)
    }
}
