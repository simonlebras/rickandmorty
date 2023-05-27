package app.rickandmorty.okhttp

import dagger.BindsOptionalOf
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Optional
import javax.inject.Singleton
import kotlin.jvm.optionals.getOrNull
import okhttp3.Interceptor
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
internal interface OkHttpClientModule {
    @BindsOptionalOf
    @HttpLogging
    fun bindHttpLoggingInterceptor(): Interceptor

    companion object {
        @Provides
        @Singleton
        fun provideOkHttpClient(
            @HttpLogging httpLoggingInterceptor: Optional<Interceptor>,
        ): OkHttpClient = OkHttpClient.Builder()
            .apply {
                httpLoggingInterceptor.getOrNull()?.let {
                    addInterceptor(it)
                }
            }
            .build()
    }
}
