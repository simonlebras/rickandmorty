package app.rickandmorty.core.designsystem.component

import androidx.annotation.FloatRange
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.lerp
import kotlin.math.max

@Stable
public interface PlaceholderHighlight {
    public val animationSpec: InfiniteRepeatableSpec<Float>?

    public fun brush(
        @FloatRange(from = 0.0, to = 1.0) progress: Float,
        size: Size,
    ): Brush

    @FloatRange(from = 0.0, to = 1.0)
    public fun alpha(progress: Float): Float

    public companion object {
        @Composable
        public fun shimmer(
            animationSpec: InfiniteRepeatableSpec<Float> = PlaceholderDefaults.shimmerAnimationSpec,
            @FloatRange(from = 0.0, to = 1.0) progressForMaxAlpha: Float = 0.6f,
        ): PlaceholderHighlight = Shimmer(
            highlightColor = PlaceholderDefaults.shimmerHighlightColor(),
            animationSpec = animationSpec,
            progressForMaxAlpha = progressForMaxAlpha,
        )
    }
}

private data class Shimmer(
    private val highlightColor: Color,
    override val animationSpec: InfiniteRepeatableSpec<Float>,
    private val progressForMaxAlpha: Float = 0.6f,
) : PlaceholderHighlight {
    override fun brush(
        progress: Float,
        size: Size,
    ): Brush = Brush.radialGradient(
        colors = listOf(
            highlightColor.copy(alpha = 0f),
            highlightColor,
            highlightColor.copy(alpha = 0f),
        ),
        center = Offset(x = 0f, y = 0f),
        radius = (max(size.width, size.height) * progress * 2).coerceAtLeast(0.01f),
    )

    override fun alpha(progress: Float): Float = when {
        // From 0f...ProgressForOpaqueAlpha we animate from 0..1
        progress <= progressForMaxAlpha -> {
            lerp(
                start = 0f,
                stop = 1f,
                fraction = progress / progressForMaxAlpha,
            )
        }
        // From ProgressForOpaqueAlpha..1f we animate from 1..0
        else -> {
            lerp(
                start = 1f,
                stop = 0f,
                fraction = (progress - progressForMaxAlpha) / (1f - progressForMaxAlpha),
            )
        }
    }
}
