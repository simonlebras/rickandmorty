package app.rickandmorty.strictmode

import android.content.Context
import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import android.os.strictmode.UntaggedSocketViolation
import androidx.startup.Initializer
import java.util.concurrent.Executors

internal class StrictModeInitializer : Initializer<Unit> {
    // TODO inject default dispatcher
    private val penaltyListenerExecutor by lazy(LazyThreadSafetyMode.NONE) {
        Executors.newSingleThreadExecutor()
    }

    override fun create(context: Context) {
        val threadPolicy = ThreadPolicy.Builder()
            .detectAll()
            .apply {
                if (Build.VERSION.SDK_INT < 28) {
                    penaltyLog()
                } else {
                    penaltyListener(penaltyListenerExecutor) {
                        // TODO log violations
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
                    penaltyListener(penaltyListenerExecutor) { violation ->
                        when (violation) {
                            is UntaggedSocketViolation -> {
                                // Firebase and OkHttp don't tag sockets
                                return@penaltyListener
                            }
                        }

                        // TODO log violations
                    }
                }
            }
            .build()
        StrictMode.setVmPolicy(vmPolicy)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
