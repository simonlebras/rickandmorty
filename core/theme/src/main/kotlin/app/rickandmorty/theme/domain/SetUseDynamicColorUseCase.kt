package app.rickandmorty.theme.domain

import app.rickandmorty.coroutines.ApplicationScope
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

public class SetUseDynamicColorUseCase @Inject internal constructor(
    private val themeRepository: ThemeRepository,
    @ApplicationScope private val applicationScope: CoroutineScope,
) {
    public suspend operator fun invoke(useDynamicColor: Boolean) {
        applicationScope.launch {
            themeRepository.setUseDynamicColor(useDynamicColor)
        }.join()
    }
}
