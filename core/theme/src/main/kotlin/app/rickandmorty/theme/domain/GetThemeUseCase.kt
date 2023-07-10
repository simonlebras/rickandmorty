package app.rickandmorty.theme.domain

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

public class GetThemeUseCase @Inject internal constructor(
    private val themeRepository: ThemeRepository,
) {
    public operator fun invoke(): Flow<Theme> = themeRepository.getTheme()
}
