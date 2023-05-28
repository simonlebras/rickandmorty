package app.rickandmorty.strictmode

import android.content.Context
import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import android.os.strictmode.UntaggedSocketViolation
import androidx.startup.Initializer
import app.rickandmorty.logging.Logger
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Inject

private const val TAG = "StrictMode"

internal class StrictModeInitializer : Initializer<Unit> {
    private val penaltyListenerExecutor by lazy(LazyThreadSafetyMode.NONE) {
        Executors.newSingleThreadExecutor()
    }

    @Inject
    lateinit var logger: Logger

    override fun create(context: Context) {
        EntryPointAccessors
            .fromApplication<StrictModeInitializerEntryPoint>(context)
            .inject(this)

        val threadPolicy = ThreadPolicy.Builder()
            .detectAll()
            .apply {
                if (Build.VERSION.SDK_INT < 28) {
                    penaltyLog()
                } else {
                    penaltyListener(penaltyListenerExecutor) { violation ->
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
                    penaltyListener(penaltyListenerExecutor) { violation ->
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

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}

@EntryPoint
@InstallIn(SingletonComponent::class)
internal interface StrictModeInitializerEntryPoint {
    fun inject(initializer: StrictModeInitializer)
}
