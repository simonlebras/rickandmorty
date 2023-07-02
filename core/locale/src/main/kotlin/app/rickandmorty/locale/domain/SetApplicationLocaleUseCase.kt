package app.rickandmorty.locale.domain

import app.rickandmorty.coroutines.ApplicationScope
import app.rickandmorty.coroutines.MainDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

public class SetApplicationLocaleUseCase @Inject internal constructor(
    private val localeRepository: LocaleRepository,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @ApplicationScope private val applicationScope: CoroutineScope,
) {
    public suspend operator fun invoke(locale: Locale?): Unit = withContext(mainDispatcher) {
        applicationScope.launch {
            localeRepository.setApplicationLocale(locale)
        }.join()
    }
}
