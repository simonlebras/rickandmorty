package app.rickandmorty.core.graphql.client

import com.apollographql.apollo.annotations.ApolloExperimental
import com.apollographql.apollo.network.NetworkMonitor
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@OptIn(ApolloExperimental::class)
@ContributesTo(AppScope::class)
public interface NetworkMonitorProvider {
  public companion object {
    @Provides public fun provideNetworkMonitor(): NetworkMonitor = NetworkMonitor()
  }
}
