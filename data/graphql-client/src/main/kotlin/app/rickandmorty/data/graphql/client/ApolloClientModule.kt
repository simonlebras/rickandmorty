package app.rickandmorty.data.graphql.client

import android.content.Context
import app.rickandmorty.core.coroutines.IODispatcher
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.annotations.ApolloExperimental
import com.apollographql.apollo.interceptor.RetryOnErrorInterceptor
import com.apollographql.apollo.network.NetworkMonitor
import com.apollographql.apollo.network.okHttpCallFactory
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
    @Singleton
    fun provideApolloClient(
        @ApplicationContext context: Context,
        okHttpClient: Lazy<OkHttpClient>,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
    ): ApolloClient = ApolloClient.Builder()
        .serverUrl(SERVER_URL)
        .retryOnErrorInterceptor(RetryOnErrorInterceptor(NetworkMonitor(context)))
        .failFastIfOffline(true)
        .failFastIfOffline(true)
        .okHttpCallFactory { okHttpClient.get() }
        .dispatcher(ioDispatcher)
        .enableAutoPersistedQueries(true)
        .build()
}
