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
    @get:Binds
    @get:IntoSet
    val CrashlyticsTree.bindTree: Timber.Tree

    companion object {
        @Provides
        fun provideFirebaseCrashlytics(): FirebaseCrashlytics = FirebaseCrashlytics.getInstance()
    }
}
