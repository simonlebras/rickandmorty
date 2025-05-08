package app.rickandmorty.core.processlifecycle.inject

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner as AndroidxProcessLifecycleOwner
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@ContributesTo(AppScope::class)
public interface ProcessLifecycleOwnerProvider {
  public companion object {
    @Provides
    @ProcessLifecycleOwner
    public fun provideProcessLifecycleOwner(): LifecycleOwner = AndroidxProcessLifecycleOwner.get()
  }
}
