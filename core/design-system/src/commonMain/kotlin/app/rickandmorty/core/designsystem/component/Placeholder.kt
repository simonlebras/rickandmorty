package app.rickandmorty.core.designsystem.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.withSaveLayer
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.invalidateDraw
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.LayoutDirection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
public object PlaceholderDefaults {
    public val shimmerAnimationSpec: InfiniteRepeatableSpec<Float> by lazy {
        infiniteRepeatable(
            animation = tween(durationMillis = 1700, delayMillis = 200),
            repeatMode = RepeatMode.Restart,
        )
    }

    @Composable
    @ReadOnlyComposable
    public fun color(
        backgroundColor: Color = MaterialTheme.colorScheme.surface,
        contentColor: Color = contentColorFor(backgroundColor),
        contentAlpha: Float = 0.1f,
    ): Color = contentColor.copy(contentAlpha).compositeOver(backgroundColor)

    @Composable
    @ReadOnlyComposable
    public fun shimmerHighlightColor(
        backgroundColor: Color = MaterialTheme.colorScheme.inverseSurface,
        alpha: Float = 0.75f,
    ): Color = backgroundColor.copy(alpha = alpha)
}

public fun Modifier.placeholder(
    visible: Boolean,
    color: Color,
    shape: Shape = RectangleShape,
    highlight: PlaceholderHighlight? = null,
    placeholderFadeAnimationSpec: AnimationSpec<Float> = spring(),
    contentFadeAnimationSpec: AnimationSpec<Float> = spring(),
): Modifier = this then PlaceholderElement(
    visible = visible,
    color = color,
    shape = shape,
    highlight = highlight,
    placeholderFadeAnimationSpec = placeholderFadeAnimationSpec,
    contentFadeAnimationSpec = contentFadeAnimationSpec,
)

private data class PlaceholderElement(
    private val visible: Boolean,
    private val color: Color,
    private val shape: Shape,
    private val highlight: PlaceholderHighlight?,
    private val placeholderFadeAnimationSpec: AnimationSpec<Float>,
    private val contentFadeAnimationSpec: AnimationSpec<Float>,
) : ModifierNodeElement<PlaceholderNode>() {
    override fun create(): PlaceholderNode = PlaceholderNode(
        visible = visible,
        color = color,
        shape = shape,
        highlight = highlight,
        placeholderFadeAnimationSpec = placeholderFadeAnimationSpec,
        contentFadeAnimationSpec = contentFadeAnimationSpec,
    )

    override fun update(node: PlaceholderNode) {
        node.update(
            visible = visible,
            color = color,
            shape = shape,
            highlight = highlight,
            placeholderFadeAnimationSpec = placeholderFadeAnimationSpec,
            contentFadeAnimationSpec = contentFadeAnimationSpec,
        )
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "placeholder"
        value = visible
        properties["visible"] = visible
        properties["color"] = color
        properties["highlight"] = highlight
        properties["shape"] = shape
    }
}

private class PlaceholderNode(
    private var visible: Boolean,
    private var color: Color,
    private var shape: Shape = RectangleShape,
    private var highlight: PlaceholderHighlight? = null,
    private var placeholderFadeAnimationSpec: AnimationSpec<Float>,
    private var contentFadeAnimationSpec: AnimationSpec<Float>,
) : Modifier.Node(),
    DrawModifierNode {
    private val crossfadeTransitionState = MutableTransitionState(visible).apply {
        targetState = visible
    }

    private var contentAlpha: Float = if (visible) 0F else 1F
    private var placeholderAlpha: Float = if (visible) 1F else 0F
    private var paint: Paint = Paint()

    // The current highlight animation progress
    private var highlightProgress: Float = 0F

    // Values used for caching purposes
    private var lastSize: Size = Size.Unspecified
    private var lastLayoutDirection: LayoutDirection? = null
    private var lastOutline: Outline? = null

    private val animatedPlaceholderAlpha = Animatable(placeholderAlpha)
    private val animatedContentAlpha = Animatable(contentAlpha)
    private val animatedHighlightProgress = Animatable(0F)

    override fun onAttach() {
        coroutineScope.runAlphaAnimations()
        coroutineScope.runHighlightAnimation()
    }

    override fun ContentDrawScope.draw() {
        val drawContent = ::drawContent

        // Draw the composable content first
        if (contentAlpha in 0.01F..0.99F) {
            // If the content alpha is between 1% and 99%, draw it in a layer with
            // the alpha applied
            paint.alpha = contentAlpha
            withLayer(paint) {
                drawContent()
            }
        } else if (contentAlpha >= 0.99F) {
            // If the content alpha is > 99%, draw it with no alpha
            drawContent()
        }

        if (placeholderAlpha in 0.01F..0.99F) {
            // If the placeholder alpha is between 1% and 99%, draw it in a layer with
            // the alpha applied
            paint.alpha = placeholderAlpha
            withLayer(paint) {
                lastOutline = drawPlaceholder(
                    shape = shape,
                    color = color,
                    highlight = highlight,
                    progress = highlightProgress,
                    lastOutline = lastOutline,
                    lastLayoutDirection = lastLayoutDirection,
                    lastSize = lastSize,
                )
            }
        } else if (placeholderAlpha >= 0.99F) {
            // If the placeholder alpha is > 99%, draw it with no alpha
            lastOutline = drawPlaceholder(
                shape = shape,
                color = color,
                highlight = highlight,
                progress = highlightProgress,
                lastOutline = lastOutline,
                lastLayoutDirection = lastLayoutDirection,
                lastSize = lastSize,
            )
        }

        // Keep track of the last size & layout direction
        lastSize = size
        lastLayoutDirection = layoutDirection
    }

    fun update(
        visible: Boolean,
        color: Color,
        shape: Shape,
        highlight: PlaceholderHighlight?,
        placeholderFadeAnimationSpec: AnimationSpec<Float>,
        contentFadeAnimationSpec: AnimationSpec<Float>,
    ) {
        var runAlphaAnimations = false
        var runHighlightAnimation = false

        if (this.visible != visible) {
            this.visible = visible
            crossfadeTransitionState.targetState = visible
            runAlphaAnimations = true
            runHighlightAnimation = true
        }

        if (this.color != color) {
            this.color = color
        }

        if (this.shape != shape) {
            this.shape = shape
        }

        if (this.highlight != highlight) {
            this.highlight = highlight
            runHighlightAnimation = true
        }

        if (this.placeholderFadeAnimationSpec != placeholderFadeAnimationSpec) {
            this.placeholderFadeAnimationSpec = placeholderFadeAnimationSpec
            runAlphaAnimations = true
        }

        if (this.contentFadeAnimationSpec != contentFadeAnimationSpec) {
            this.contentFadeAnimationSpec = contentFadeAnimationSpec
            runAlphaAnimations = true
        }

        if (runAlphaAnimations) {
            coroutineScope.runAlphaAnimations()
        }
        if (runHighlightAnimation) {
            coroutineScope.runHighlightAnimation()
        }
    }

    private fun CoroutineScope.runAlphaAnimations() {
        launch {
            animatedPlaceholderAlpha.animateTo(
                targetValue = if (visible) 1F else 0F,
                placeholderFadeAnimationSpec,
            ) {
                val previousPlaceholderAlpha = placeholderAlpha
                placeholderAlpha = value
                if (previousPlaceholderAlpha < 0.01F && placeholderAlpha >= 0.01F && !visible) {
                    coroutineScope.runHighlightAnimation()
                }
                invalidateDraw()
            }
        }

        launch {
            animatedContentAlpha.animateTo(
                targetValue = if (visible) 0F else 1F,
                contentFadeAnimationSpec,
            ) {
                contentAlpha = value
                invalidateDraw()
            }
        }
    }

    private fun CoroutineScope.runHighlightAnimation() {
        val isEffectivelyVisible = visible || placeholderAlpha >= 0.01F
        val animationSpec = highlight?.animationSpec
        if (isEffectivelyVisible && animationSpec != null) {
            launch {
                animatedHighlightProgress.animateTo(1F, animationSpec) {
                    highlightProgress = value
                    invalidateDraw()
                }
            }
        }
    }
}

private inline fun DrawScope.withLayer(
    paint: Paint,
    block: DrawScope.() -> Unit,
) {
    drawIntoCanvas { canvas ->
        canvas.withSaveLayer(bounds = size.toRect(), paint = paint) {
            block()
        }
    }
}

private fun DrawScope.drawPlaceholder(
    color: Color,
    shape: Shape,
    highlight: PlaceholderHighlight?,
    progress: Float,
    lastOutline: Outline?,
    lastLayoutDirection: LayoutDirection?,
    lastSize: Size?,
): Outline? {
    // shortcut to avoid Outline calculation and allocation
    if (shape === RectangleShape) {
        // Draw the initial background color
        drawRect(color = color)

        if (highlight != null) {
            drawRect(
                brush = highlight.brush(progress, size),
                alpha = highlight.alpha(progress),
            )
        }
        // We didn't create an outline so return null
        return null
    }

    // Otherwise we need to create an outline from the shape
    val outline = lastOutline.takeIf {
        size == lastSize && layoutDirection == lastLayoutDirection
    } ?: shape.createOutline(size, layoutDirection, this)

    // Draw the placeholder color
    drawOutline(outline = outline, color = color)

    if (highlight != null) {
        drawOutline(
            outline = outline,
            brush = highlight.brush(progress, size),
            alpha = highlight.alpha(progress),
        )
    }

    // Return the outline we used
    return outline
}
