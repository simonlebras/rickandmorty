package app.rickandmorty.core.processlifecycle.inject

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner as AndroidxProcessLifecycleOwner
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

@ContributesTo(AppScope::class)
public interface ProcessLifecycleOwnerComponent {
  @Provides
  public fun provideProcessLifecycleOwner(): @ProcessLifecycleOwner LifecycleOwner =
    AndroidxProcessLifecycleOwner.get()
}
