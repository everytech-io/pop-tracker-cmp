package io.everytech.poptracker.ui.components.everyapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Immutable
data class EveryImageBoxConfig(
    val aspectRatio: Float = 1f, // 1f = square, 16/9f = landscape, 9/16f = portrait
    val contentScale: ContentScale = ContentScale.Crop,
    val shape: Shape = RoundedCornerShape(0.dp),
    val backgroundColor: Color? = null,
    val contentDescription: String? = null
)

@Composable
fun EveryImageBox(
    painter: Painter,
    modifier: Modifier = Modifier,
    config: EveryImageBoxConfig = EveryImageBoxConfig()
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(config.aspectRatio)
            .clip(config.shape)
            .then(
                if (config.backgroundColor != null) {
                    Modifier.background(config.backgroundColor)
                } else {
                    Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = config.contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = config.contentScale
        )
    }
}

object AspectRatios {
    const val SQUARE = 1f
    const val LANDSCAPE_16_9 = 16f / 9f
    const val LANDSCAPE_4_3 = 4f / 3f
    const val PORTRAIT_9_16 = 9f / 16f
    const val PORTRAIT_3_4 = 3f / 4f
    const val GOLDEN = 1.618f
}