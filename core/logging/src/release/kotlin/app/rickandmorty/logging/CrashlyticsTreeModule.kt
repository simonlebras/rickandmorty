package app.rickandmorty.logging

import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
internal interface CrashlyticsTreeModule {
    @Binds
    @IntoSet
    fun bindTree(crashlyticsTree: CrashlyticsTree): Timber.Tree

    companion object {
        @Provides
        fun provideFirebaseCrashlytics(): FirebaseCrashlytics = FirebaseCrashlytics.getInstance()
    }
}
