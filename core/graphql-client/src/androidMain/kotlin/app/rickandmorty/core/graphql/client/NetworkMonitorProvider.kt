package app.rickandmorty.core.graphql.client

import android.content.Context
import app.rickandmorty.core.metro.AppContext
import com.apollographql.apollo.annotations.ApolloExperimental
import com.apollographql.apollo.network.NetworkMonitor
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@OptIn(ApolloExperimental::class)
@BindingContainer
@ContributesTo(AppScope::class)
public object NetworkMonitorProvider {
  @Provides
  public fun provideNetworkMonitor(@AppContext context: Context): NetworkMonitor =
    NetworkMonitor(context)
}
