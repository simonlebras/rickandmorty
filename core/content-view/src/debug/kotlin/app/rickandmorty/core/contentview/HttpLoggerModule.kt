package app.rickandmorty.core.contentview

import android.content.Context
import app.rickandmorty.core.okhttp.HttpLogging
import au.com.gridstone.debugdrawer.okhttplogs.HttpLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton
import okhttp3.Interceptor

@Module
@InstallIn(SingletonComponent::class)
internal object HttpLoggerModule {
    @Provides
    @Singleton
    fun provideHttpLogger(
        @ApplicationContext context: Context,
    ): HttpLogger = HttpLogger(context)

    @Provides
    @IntoSet
    @HttpLogging
    fun provideHttpLoggerInterceptor(
        httpLogger: HttpLogger,
    ): Interceptor = httpLogger.interceptor
}
