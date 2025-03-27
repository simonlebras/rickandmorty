package app.rickandmorty.core.coroutines.inject

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
public interface CoroutineScopesComponent {
    @Provides
    @SingleIn(AppScope::class)
    public fun provideApplicationScope(
        @MainDispatcher mainDispatcher: CoroutineContext,
    ): @ApplicationScope CoroutineScope = CoroutineScope(SupervisorJob() + mainDispatcher)
}
