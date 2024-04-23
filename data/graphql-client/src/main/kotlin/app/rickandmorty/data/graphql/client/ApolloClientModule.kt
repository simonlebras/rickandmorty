package app.rickandmorty.data.graphql.client

import app.rickandmorty.core.coroutines.IODispatcher
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpCallFactory
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
    @Singleton
    fun provideApolloClient(
        okHttpClient: Lazy<OkHttpClient>,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(SERVER_URL)
            .okHttpCallFactory { okHttpClient.get() }
            .dispatcher(ioDispatcher)
            .enableAutoPersistedQueries(true)
            .build()
    }
}
