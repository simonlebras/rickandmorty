package app.rickandmorty.data.graphql.client

import android.content.Context
import app.rickandmorty.core.coroutines.inject.IODispatcher
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.annotations.ApolloExperimental
import com.apollographql.apollo.interceptor.RetryOnErrorInterceptor
import com.apollographql.apollo.network.NetworkMonitor
import kotlinx.coroutines.CoroutineDispatcher

private const val SERVER_URL = "https://rickandmortyapi.com/graphql"

@OptIn(ApolloExperimental::class)
internal class ApolloClientModule {
    fun provideApolloClient(
        context: Context,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): ApolloClient = ApolloClient.Builder()
        .serverUrl(SERVER_URL)
        .retryOnErrorInterceptor(RetryOnErrorInterceptor(NetworkMonitor(context)))
        .failFastIfOffline(true)
        .failFastIfOffline(true)
        .dispatcher(ioDispatcher)
        .enableAutoPersistedQueries(true)
        .build()
}
