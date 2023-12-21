package app.rickandmorty.locale.domain

import app.rickandmorty.core.coroutines.IODispatcher
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

public class GetAvailableApplicationLocalesUseCase @Inject internal constructor(
    private val localeRepository: LocaleRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    public suspend operator fun invoke(): ImmutableList<Locale> = withContext(ioDispatcher) {
        localeRepository.getAvailableApplicationLocales()
    }
}
