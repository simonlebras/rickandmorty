package app.rickandmorty.jankstats

import android.app.Activity
import androidx.metrics.performance.JankStats
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
internal object JankStatsModule {
    @Provides
    fun providesJankStats(activity: Activity): JankStats {
        val frameListener = JankStats.OnFrameListener { frameData ->
            if (frameData.isJank) {
                // TODO log jank
            }
        }
        return JankStats.createAndTrack(activity.window, frameListener)
    }
}
