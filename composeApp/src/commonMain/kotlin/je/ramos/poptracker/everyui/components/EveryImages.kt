package je.ramos.poptracker.everyui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import je.ramos.poptracker.everyui.theme.AppTheme
import je.ramos.poptracker.everyui.theme.AspectRatioToken
import je.ramos.poptracker.everyui.theme.PaddingToken
import je.ramos.poptracker.everyui.theme.ShapeToken
import je.ramos.poptracker.everyui.theme.toDp
import je.ramos.poptracker.everyui.theme.toFloat
import je.ramos.poptracker.everyui.theme.toShape
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import poptracker.composeapp.generated.resources.Res
import poptracker.composeapp.generated.resources.labubu_demo

sealed class ImageSource {
    data class Drawable(val painter: Painter) : ImageSource()
    data class Url(val url: String) : ImageSource()
}

/**
 * Configuration for EveryImage component with semantic aspect ratios.
 *
 * @param aspectRatio Semantic aspect ratio token:
 *   - Square (1:1) - Perfect for profile pictures, icons
 *   - Landscape (16:9) - Video/cinema format  
 *   - Portrait (9:16) - Phone screen format
 *   - Photo (4:3) - Traditional photo format
 *   - PhotoPortrait (3:4) - Portrait photo format
 *   - Ultrawide (21:9) - Ultrawide display format
 *   - Golden (1.618:1) - Golden ratio for artistic layouts
 * @param contentScale How the image should be scaled within its bounds
 * @param shape Corner radius using semantic tokens (None, Small, Medium, Large, ExtraLarge)
 * @param padding Internal padding using semantic tokens (None, Small, Medium, Large, ExtraLarge)
 * @param backgroundColor Background color behind the image
 * @param showLoadingIndicator Whether to show a circular progress indicator while loading
 * @param loadingIndicatorColor Color of the loading indicator
 * @param errorIcon Custom icon to show when image fails to load
 * @param errorIconTint Tint color for the error icon
 */
@Immutable
data class EveryImageConfig(
    val aspectRatio: AspectRatioToken = AspectRatioToken.Square,
    val contentScale: ContentScale = ContentScale.Crop,
    val shape: ShapeToken = ShapeToken.Medium,
    val padding: PaddingToken = PaddingToken.None,
    val backgroundColor: Color = Color.Unspecified,
    val showLoadingIndicator: Boolean = true,
    val loadingIndicatorColor: Color = Color.Unspecified,
    val errorIcon: Painter? = null,
    val errorIconTint: Color = Color.Unspecified
)

/**
 * Atomic image component with opinionated aspect ratios and loading states.
 *
 * Supports both drawable resources and URL loading with automatic loading 
 * indicators and error handling. Uses semantic tokens for consistent
 * design system integration.
 *
 * @param source Image source - either [ImageSource.Drawable] for resources or [ImageSource.Url] for remote images
 * @param contentDescription Accessibility description for the image
 * @param modifier Modifier for the component
 * @param config Configuration object with aspect ratio, shape, padding, and visual settings
 *
 * @sample EveryImagePreview
 */
@Composable
fun EveryImage(
    source: ImageSource,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    config: EveryImageConfig = EveryImageConfig()
) {
    Box(
        modifier = modifier
            .aspectRatio(config.aspectRatio.toFloat())
            .padding(config.padding.toDp())
            .clip(config.shape.toShape())
            .background(
                if (config.backgroundColor != Color.Unspecified) {
                    config.backgroundColor
                } else {
                    Color.Black.copy(alpha = 0.1f)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        when (source) {
            is ImageSource.Drawable -> {
                Image(
                    painter = source.painter,
                    contentDescription = contentDescription,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = config.contentScale
                )
            }
            is ImageSource.Url -> {
                // TODO: Implement URL loading when image loading library is added
                // For now, show placeholder
                LoadingState(config = config)
            }
        }
    }
}

@Composable
private fun LoadingState(config: EveryImageConfig) {
    if (config.showLoadingIndicator) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = if (config.loadingIndicatorColor != Color.Unspecified) {
                config.loadingIndicatorColor
            } else {
                LocalContentColor.current.copy(alpha = 0.6f)
            }
        )
    }
}

@Composable
private fun ErrorState(config: EveryImageConfig) {
    if (config.errorIcon != null) {
        Icon(
            painter = config.errorIcon,
            contentDescription = "Failed to load image",
            modifier = Modifier.size(32.dp),
            tint = if (config.errorIconTint != Color.Unspecified) {
                config.errorIconTint
            } else {
                LocalContentColor.current.copy(alpha = 0.6f)
            }
        )
    }
}

// Preview Section
@Preview
@Composable
fun EveryImagePreview() {
    AppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Default config
            EveryImage(
                source = ImageSource.Drawable(painterResource(Res.drawable.labubu_demo)),
                contentDescription = "Labubu Demo",
                modifier = Modifier.size(120.dp)
            )
            
            // Different shapes
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EveryImage(
                    source = ImageSource.Drawable(painterResource(Res.drawable.labubu_demo)),
                    modifier = Modifier.size(80.dp),
                    config = EveryImageConfig(shape = ShapeToken.None)
                )
                EveryImage(
                    source = ImageSource.Drawable(painterResource(Res.drawable.labubu_demo)),
                    modifier = Modifier.size(80.dp),
                    config = EveryImageConfig(shape = ShapeToken.Small)
                )
                EveryImage(
                    source = ImageSource.Drawable(painterResource(Res.drawable.labubu_demo)),
                    modifier = Modifier.size(80.dp),
                    config = EveryImageConfig(shape = ShapeToken.Large)
                )
                EveryImage(
                    source = ImageSource.Drawable(painterResource(Res.drawable.labubu_demo)),
                    modifier = Modifier.size(80.dp),
                    config = EveryImageConfig(shape = ShapeToken.ExtraLarge)
                )
            }
            
            // Different aspect ratios
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EveryImage(
                    source = ImageSource.Drawable(painterResource(Res.drawable.labubu_demo)),
                    modifier = Modifier.width(100.dp),
                    config = EveryImageConfig(aspectRatio = AspectRatioToken.Landscape)
                )
                EveryImage(
                    source = ImageSource.Drawable(painterResource(Res.drawable.labubu_demo)),
                    modifier = Modifier.width(60.dp),
                    config = EveryImageConfig(aspectRatio = AspectRatioToken.Portrait)
                )
                EveryImage(
                    source = ImageSource.Drawable(painterResource(Res.drawable.labubu_demo)),
                    modifier = Modifier.width(80.dp),
                    config = EveryImageConfig(aspectRatio = AspectRatioToken.Photo)
                )
            }
            
            // With padding
            EveryImage(
                source = ImageSource.Drawable(painterResource(Res.drawable.labubu_demo)),
                modifier = Modifier.size(140.dp),
                config = EveryImageConfig(
                    padding = PaddingToken.Large,
                    backgroundColor = Color.Blue.copy(alpha = 0.1f)
                )
            )
        }
    }
}

@Preview
@Composable
fun EveryImageLoadingPreview() {
    AppTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Loading state (URL will show loading)
            EveryImage(
                source = ImageSource.Url("https://example.com/image.jpg"),
                modifier = Modifier.size(100.dp),
                config = EveryImageConfig(
                    showLoadingIndicator = true,
                    loadingIndicatorColor = Color.Blue
                )
            )
            
            // Loading without indicator
            EveryImage(
                source = ImageSource.Url("https://example.com/image.jpg"),
                modifier = Modifier.size(100.dp),
                config = EveryImageConfig(
                    showLoadingIndicator = false,
                    backgroundColor = Color.Gray.copy(alpha = 0.2f)
                )
            )
        }
    }
}

@Preview
@Composable
fun EveryImageVariationsPreview() {
    AppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ContentScale variations
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EveryImage(
                    source = ImageSource.Drawable(painterResource(Res.drawable.labubu_demo)),
                    modifier = Modifier.size(80.dp),
                    config = EveryImageConfig(contentScale = ContentScale.Crop)
                )
                EveryImage(
                    source = ImageSource.Drawable(painterResource(Res.drawable.labubu_demo)),
                    modifier = Modifier.size(80.dp),
                    config = EveryImageConfig(contentScale = ContentScale.Fit)
                )
                EveryImage(
                    source = ImageSource.Drawable(painterResource(Res.drawable.labubu_demo)),
                    modifier = Modifier.size(80.dp),
                    config = EveryImageConfig(contentScale = ContentScale.FillBounds)
                )
            }
        }
    }
}

