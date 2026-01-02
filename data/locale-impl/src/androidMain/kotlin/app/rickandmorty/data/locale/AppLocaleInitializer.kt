package app.rickandmorty.data.locale

import android.app.Application
import android.app.LocaleManager
import android.content.ComponentName
import android.os.Build
import android.os.LocaleList
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.getSystemService
import androidx.core.os.LocaleListCompat
import app.rickandmorty.core.base.doOnActivityPreCreated
import app.rickandmorty.core.base.isComponentEnabled
import app.rickandmorty.core.base.setComponentEnabled
import app.rickandmorty.core.base.unsafeLazy
import app.rickandmorty.core.coroutines.awaitBlocking
import app.rickandmorty.core.coroutines.inject.ApplicationScope
import app.rickandmorty.core.coroutines.inject.IODispatcher
import app.rickandmorty.core.startup.Initializer
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ContributesIntoSet(AppScope::class)
public class AppLocaleInitializer(
  application: Application,
  private val localeRepository: LocaleRepository,
  @ApplicationScope private val applicationScope: CoroutineScope,
  @IODispatcher private val ioDispatcher: CoroutineContext,
) : Initializer {
  private val appLocaleImpl =
    if (Build.VERSION.SDK_INT >= 33) {
      AppLocale33Impl(application)
    } else {
      AppLocale24Impl(application)
    }

  override fun initialize() {
    val appLocaleDeferred = getAppLocaleDeferred()
    appLocaleImpl.initializeAppLocale(appLocaleDeferred)

    observeAppLocaleUpdates()
  }

  private fun getAppLocaleDeferred(): Deferred<Locale?> =
    applicationScope.async(context = ioDispatcher, start = CoroutineStart.LAZY) {
      localeRepository.getAppLocale().first()
    }

  private fun observeAppLocaleUpdates() {
    localeRepository
      .getAppLocale()
      .onEach { locale -> appLocaleImpl.setAppLocale(locale) }
      .launchIn(applicationScope)
  }
}

private interface AppLocaleImpl {
  fun initializeAppLocale(localeDeferred: Deferred<Locale?>)

  fun setAppLocale(locale: Locale?)
}

@RequiresApi(33)
private class AppLocale33Impl(private val application: Application) : AppLocaleImpl {
  private val localeManager by unsafeLazy { application.getSystemService<LocaleManager>()!! }

  override fun initializeAppLocale(localeDeferred: Deferred<Locale?>) {
    val appLocaleComponent = ComponentName(application, AppLocaleService::class.java)
    if (!application.isComponentEnabled(appLocaleComponent)) {
      if (localeManager.applicationLocales.isEmpty) {
        localeDeferred.start()
        application.doOnActivityPreCreated {
          val locale = localeDeferred.awaitBlocking()
          setAppLocale(locale)

          application.setComponentEnabled(appLocaleComponent, true)
        }
      } else {
        application.setComponentEnabled(appLocaleComponent, true)
      }
    }
  }

  override fun setAppLocale(locale: Locale?) {
    localeManager.applicationLocales = locale.toLocaleList()
  }

  private fun Locale?.toLocaleList(): LocaleList =
    when (this) {
      null -> LocaleList.getEmptyLocaleList()
      else -> LocaleList.forLanguageTags(toLanguageTag())
    }
}

private class AppLocale24Impl(private val application: Application) : AppLocaleImpl {
  override fun initializeAppLocale(localeDeferred: Deferred<Locale?>) {
    localeDeferred.start()
    application.doOnActivityPreCreated {
      val locale = localeDeferred.awaitBlocking()
      setAppLocale(locale)
    }
  }

  override fun setAppLocale(locale: Locale?) {
    AppCompatDelegate.setApplicationLocales(locale.toLocaleListCompat())
  }

  private fun Locale?.toLocaleListCompat(): LocaleListCompat =
    when (this) {
      null -> LocaleListCompat.getEmptyLocaleList()
      else -> LocaleListCompat.create(platformLocale)
    }
}
