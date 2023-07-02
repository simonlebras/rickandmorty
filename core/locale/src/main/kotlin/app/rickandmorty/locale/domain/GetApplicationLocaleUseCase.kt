package app.rickandmorty.locale.domain

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

public class GetApplicationLocaleUseCase @Inject internal constructor(
    private val localeRepository: LocaleRepository,
) {
    public operator fun invoke(): Flow<Locale?> = localeRepository.getApplicationLocale()
}
