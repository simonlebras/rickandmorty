package app.rickandmorty.theme.domain

import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList

public class GetAvailableNightModesUseCase @Inject internal constructor(
    private val themeRepository: ThemeRepository,
) {
    public operator fun invoke(): ImmutableList<NightMode> =
        themeRepository.getAvailableNightModes()
}
