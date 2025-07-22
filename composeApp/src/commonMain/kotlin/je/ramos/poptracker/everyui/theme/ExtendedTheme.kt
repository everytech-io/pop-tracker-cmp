package je.ramos.poptracker.everyui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun ExtendedTheme(
    extendedSpacing: ExtendedSpacing = ExtendedSpacing(),
    extendedShapes: ExtendedShapes = ExtendedShapes(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalExtendedSpacing provides extendedSpacing,
        LocalExtendedShapes provides extendedShapes,
        content = content
    )
}

@Composable
fun AppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme {
        ExtendedTheme {
            content()
        }
    }
}