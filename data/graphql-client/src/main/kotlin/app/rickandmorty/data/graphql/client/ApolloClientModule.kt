package app.rickandmorty.data.graphql.client

import android.content.Context
import app.rickandmorty.core.coroutines.IODispatcher
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.network.NetworkMonitor
import com.apollographql.apollo3.network.okHttpCallFactory
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

@OptIn(ApolloExperimental::class)
@Module
@InstallIn(SingletonComponent::class)
internal class ApolloClientModule {
    @Provides
    fun provideNetworkMonitor(
        @ApplicationContext context: Context,
    ): NetworkMonitor = NetworkMonitor(context)

    @Provides
    @Singleton
    fun provideApolloClient(
        networkMonitor: NetworkMonitor,
        okHttpClient: Lazy<OkHttpClient>,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(SERVER_URL)
            .networkMonitor(networkMonitor)
            .failFastIfOffline(true)
            .okHttpCallFactory { okHttpClient.get() }
            .dispatcher(ioDispatcher)
            .enableAutoPersistedQueries(true)
            .build()
    }
}
