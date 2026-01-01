package app.rickandmorty.core.graphql.client

import app.rickandmorty.core.coroutines.inject.IODispatcher
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.annotations.ApolloExperimental
import com.apollographql.apollo.interceptor.RetryOnErrorInterceptor
import com.apollographql.apollo.network.NetworkMonitor
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher

private const val SERVER_URL = "https://rickandmortyapi.com/graphql"

@OptIn(ApolloExperimental::class)
@ContributesTo(AppScope::class)
public interface ApolloClientProvider {
  public companion object {
    @Provides
    public fun provideApolloClient(
      @IODispatcher ioDispatcher: CoroutineContext,
      networkMonitor: NetworkMonitor? = null,
    ): ApolloClient =
      ApolloClient.Builder()
        .serverUrl(SERVER_URL)
        .apply {
          networkMonitor?.let { monitor ->
            retryOnErrorInterceptor(RetryOnErrorInterceptor(monitor))
          }
        }
        .failFastIfOffline(true)
        .dispatcher(ioDispatcher[CoroutineDispatcher])
        .enableAutoPersistedQueries(true)
        .build()
  }
}
