package app.rickandmorty.data.graphql.client

import app.rickandmorty.core.coroutines.IODispatcher
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.http.DefaultHttpEngine
import com.apollographql.apollo3.network.http.HttpEngine
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient

private const val SERVER_URL = "https://rickandmortyapi.com/graphql"

@Module
@InstallIn(SingletonComponent::class)
internal class ApolloClientModule {
    @Provides
    fun provideHttpEngine(client: Lazy<OkHttpClient>): HttpEngine {
        return DefaultHttpEngine { client.get().newCall(it) }
    }

    @Provides
    @Singleton
    fun provideApolloClient(
        httpEngine: HttpEngine,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(SERVER_URL)
            .httpEngine(httpEngine)
            .dispatcher(ioDispatcher)
            .enableAutoPersistedQueries(true)
            .build()
    }
}
