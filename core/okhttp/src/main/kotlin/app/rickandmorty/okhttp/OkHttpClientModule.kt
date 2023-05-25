package app.rickandmorty.okhttp

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.Multibinds
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
internal interface OkHttpClientModule {
    @Multibinds
    @HttpLogging
    fun httpLoggingInterceptors(): Set<Interceptor>

    companion object {
        @Provides
        @Singleton
        fun provideOkHttpClient(
            @HttpLogging httpLoggingInterceptors: Set<@JvmSuppressWildcards Interceptor>,
        ): OkHttpClient = OkHttpClient.Builder()
            .apply {
                httpLoggingInterceptors.forEach {
                    addInterceptor(it)
                }
            }
            .build()
    }
}
