package app.rickandmorty.core

import android.content.Context
import android.content.res.Configuration
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Singleton
public class AppDelegate @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val _configuration = MutableStateFlow(Configuration(context.resources.configuration))
    public val configuration: StateFlow<Configuration> = _configuration.asStateFlow()

    public fun onConfigurationChanged(newConfig: Configuration) {
        _configuration.value = Configuration(newConfig)
    }
}
