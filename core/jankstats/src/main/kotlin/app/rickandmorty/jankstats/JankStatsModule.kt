package app.rickandmorty.jankstats

import android.app.Activity
import androidx.metrics.performance.JankStats
import app.rickandmorty.logging.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

private const val TAG = "JankStats"

@Module
@InstallIn(ActivityComponent::class)
internal object JankStatsModule {
    @Provides
    fun providesJankStats(
        activity: Activity,
        logger: Logger,
    ): JankStats {
        val frameListener = JankStats.OnFrameListener { frameData ->
            if (frameData.isJank) {
                logger.tag(TAG).v(frameData.toString())
            }
        }
        return JankStats.createAndTrack(activity.window, frameListener)
    }
}
