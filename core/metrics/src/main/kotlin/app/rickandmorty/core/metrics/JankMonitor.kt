package app.rickandmorty.core.metrics

import android.app.Activity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.metrics.performance.JankStats
import app.rickandmorty.core.logging.Logger
import javax.inject.Inject

private const val TAG = "JankStats"

public class JankMonitor @Inject internal constructor(
    activity: Activity,
    logger: Logger,
) : DefaultLifecycleObserver {
    private val frameListener = JankStats.OnFrameListener { frameData ->
        if (frameData.isJank) {
            logger.tag(TAG).v(frameData.toString())
        }
    }

    private val jankStats = JankStats.createAndTrack(activity.window, frameListener)

    override fun onResume(owner: LifecycleOwner) {
        jankStats.isTrackingEnabled = true
    }

    override fun onPause(owner: LifecycleOwner) {
        jankStats.isTrackingEnabled = false
    }
}
