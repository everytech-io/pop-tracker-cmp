package je.ramos.poptracker.everyui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class ExtendedSpacing(
    val none: Dp = 0.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 32.dp
)

@Immutable  
data class ExtendedShapes(
    val none: Shape = RoundedCornerShape(0.dp),
    val small: Shape = RoundedCornerShape(4.dp),
    val medium: Shape = RoundedCornerShape(8.dp), 
    val large: Shape = RoundedCornerShape(12.dp),
    val extraLarge: Shape = RoundedCornerShape(16.dp)
)

val LocalExtendedSpacing = staticCompositionLocalOf { ExtendedSpacing() }
val LocalExtendedShapes = staticCompositionLocalOf { ExtendedShapes() }

// Extension properties for MaterialTheme
val MaterialTheme.extendedSpacing: ExtendedSpacing
    @Composable
    get() = LocalExtendedSpacing.current

val MaterialTheme.extendedShapes: ExtendedShapes
    @Composable
    get() = LocalExtendedShapes.current

// Semantic token access  
enum class PaddingToken {
    None, Small, Medium, Large, ExtraLarge
}

enum class ShapeToken {
    None, Small, Medium, Large, ExtraLarge
}

enum class AspectRatioToken {
    Square,      // 1:1
    Landscape,   // 16:9
    Portrait,    // 9:16
    Photo,       // 4:3
    PhotoPortrait, // 3:4
    Ultrawide,   // 21:9
    Golden       // 1.618:1
}

@Composable
fun PaddingToken.toDp(): Dp = when (this) {
    PaddingToken.None -> MaterialTheme.extendedSpacing.none
    PaddingToken.Small -> MaterialTheme.extendedSpacing.small
    PaddingToken.Medium -> MaterialTheme.extendedSpacing.medium
    PaddingToken.Large -> MaterialTheme.extendedSpacing.large
    PaddingToken.ExtraLarge -> MaterialTheme.extendedSpacing.extraLarge
}

@Composable  
fun ShapeToken.toShape(): Shape = when (this) {
    ShapeToken.None -> MaterialTheme.extendedShapes.none
    ShapeToken.Small -> MaterialTheme.extendedShapes.small
    ShapeToken.Medium -> MaterialTheme.extendedShapes.medium
    ShapeToken.Large -> MaterialTheme.extendedShapes.large
    ShapeToken.ExtraLarge -> MaterialTheme.extendedShapes.extraLarge
}

fun AspectRatioToken.toFloat(): Float = when (this) {
    AspectRatioToken.Square -> 1f
    AspectRatioToken.Landscape -> 16f / 9f
    AspectRatioToken.Portrait -> 9f / 16f
    AspectRatioToken.Photo -> 4f / 3f
    AspectRatioToken.PhotoPortrait -> 3f / 4f
    AspectRatioToken.Ultrawide -> 21f / 9f
    AspectRatioToken.Golden -> 1.618f
}