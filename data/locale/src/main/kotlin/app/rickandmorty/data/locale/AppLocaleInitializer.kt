package app.rickandmorty.data.locale

import android.app.LocaleManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppLocalesMetadataHolderService
import androidx.core.content.getSystemService
import app.rickandmorty.core.coroutines.ApplicationScope
import app.rickandmorty.core.startup.Initializer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import se.ansman.dagger.auto.AutoBindIntoSet

@AutoBindIntoSet
internal class AppLocaleInitializer @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localeRepository: LocaleRepository,
    @ApplicationScope private val applicationScope: CoroutineScope,
) : Initializer {
    override fun initialize() {
        if (Build.VERSION.SDK_INT >= 33) {
            syncAppLocaleToFramework()
        } else {
            // Set app locale during app startup
            val currentAppLocale = runBlocking {
                localeRepository
                    .getAppLocale()
                    .first()
            }
            AppCompatDelegate.setApplicationLocales(currentAppLocale.toLocaleListCompat())

            // Update app locale to match the theme
            localeRepository.getAppLocale()
                .onEach { locale ->
                    AppCompatDelegate.setApplicationLocales(locale.toLocaleListCompat())
                }
                .launchIn(applicationScope)
        }
    }

    @RequiresApi(33)
    fun syncAppLocaleToFramework() {
        val appLocalesComponent = ComponentName(
            context,
            AppLocalesMetadataHolderService::class.java,
        )
        val packageManager = context.packageManager
        if (packageManager.getComponentEnabledSetting(appLocalesComponent)
            != PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        ) {
            val localeManager = context.getSystemService<LocaleManager>()!!
            if (localeManager.applicationLocales.isEmpty) {
                val currentAppLocale = runBlocking {
                    localeRepository
                        .getAppLocale()
                        .first()
                }
                localeManager.applicationLocales = currentAppLocale.toLocaleList()
            }
        }
        packageManager.setComponentEnabledSetting(
            appLocalesComponent,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP,
        )
    }
}
