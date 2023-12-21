package app.rickandmorty.data.graphql.client

import android.content.Context
import app.rickandmorty.coroutines.IODispatcher
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.api.NormalizedCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo3.network.http.DefaultHttpEngine
import com.apollographql.apollo3.network.http.HttpEngine
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient

private const val SERVER_URL = "https://rickandmortyapi.com/graphql"

private const val MEMORY_CACHE_SIZE = 10 * 1024 * 1024 // 10 MB

@Module
@InstallIn(SingletonComponent::class)
internal class ApolloClientModule {
    @Provides
    fun provideHttpEngine(client: Lazy<OkHttpClient>): HttpEngine {
        return DefaultHttpEngine { client.get().newCall(it) }
    }

    @Provides
    @Singleton
    fun provideNormalizedCacheFactory(
        @ApplicationContext context: Context,
    ): NormalizedCacheFactory {
        return MemoryCacheFactory(maxSizeBytes = MEMORY_CACHE_SIZE).chain(
            SqlNormalizedCacheFactory(context),
        )
    }

    @Provides
    @Singleton
    fun provideApolloClient(
        httpEngine: HttpEngine,
        cacheFactory: NormalizedCacheFactory,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(SERVER_URL)
            .httpEngine(httpEngine)
            .normalizedCache(cacheFactory)
            .dispatcher(ioDispatcher)
            .enableAutoPersistedQueries(true)
            .build()
    }
}
