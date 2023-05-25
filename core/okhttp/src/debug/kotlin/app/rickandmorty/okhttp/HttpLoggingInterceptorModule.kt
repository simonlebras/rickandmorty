package app.rickandmorty.okhttp

import app.rickandmorty.logging.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

private const val TAG = "HTTP"

@Module
@InstallIn(SingletonComponent::class)
internal object HttpLoggingInterceptorModule {
    @Provides
    @IntoSet
    @HttpLogging
    fun provideHttpLoggingInterceptor(
        logger: Logger,
    ): Interceptor {
        val httpLogger = HttpLoggingInterceptor.Logger { message ->
            logger.tag(TAG).v(message)
        }
        return HttpLoggingInterceptor(httpLogger)
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}
