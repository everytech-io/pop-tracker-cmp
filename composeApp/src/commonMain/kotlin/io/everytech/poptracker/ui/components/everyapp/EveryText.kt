package io.everytech.poptracker.ui.components.everyapp

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

@Immutable
data class EveryTextConfig(
    val style: EveryTextStyle = EveryTextStyle.Body,
    val color: Color? = null,
    val fontWeight: FontWeight? = null,
    val fontSize: TextUnit? = null,
    val textAlign: TextAlign? = null,
    val maxLines: Int = Int.MAX_VALUE,
    val overflow: TextOverflow = TextOverflow.Clip,
    val softWrap: Boolean = true
)

enum class EveryTextStyle {
    Display,
    Headline,
    Title,
    TitleMedium,
    TitleSmall,
    Body,
    BodySmall,
    Label,
    LabelSmall,
    Caption
}

@Composable
fun EveryText(
    text: String,
    modifier: Modifier = Modifier,
    config: EveryTextConfig = EveryTextConfig()
) {
    val materialStyle = when (config.style) {
        EveryTextStyle.Display -> MaterialTheme.typography.displayMedium
        EveryTextStyle.Headline -> MaterialTheme.typography.headlineMedium
        EveryTextStyle.Title -> MaterialTheme.typography.titleLarge
        EveryTextStyle.TitleMedium -> MaterialTheme.typography.titleMedium
        EveryTextStyle.TitleSmall -> MaterialTheme.typography.titleSmall
        EveryTextStyle.Body -> MaterialTheme.typography.bodyLarge
        EveryTextStyle.BodySmall -> MaterialTheme.typography.bodyMedium
        EveryTextStyle.Label -> MaterialTheme.typography.labelLarge
        EveryTextStyle.LabelSmall -> MaterialTheme.typography.labelSmall
        EveryTextStyle.Caption -> MaterialTheme.typography.bodySmall
    }
    
    val textColor = config.color ?: when (config.style) {
        EveryTextStyle.Display, 
        EveryTextStyle.Headline, 
        EveryTextStyle.Title,
        EveryTextStyle.TitleMedium,
        EveryTextStyle.TitleSmall -> MaterialTheme.colorScheme.onSurface
        EveryTextStyle.Body,
        EveryTextStyle.Label -> MaterialTheme.colorScheme.onSurface
        EveryTextStyle.BodySmall,
        EveryTextStyle.LabelSmall,
        EveryTextStyle.Caption -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    
    Text(
        text = text,
        modifier = modifier,
        style = materialStyle.copy(
            fontWeight = config.fontWeight ?: materialStyle.fontWeight,
            fontSize = config.fontSize ?: materialStyle.fontSize,
            textAlign = config.textAlign ?: materialStyle.textAlign
        ),
        color = textColor,
        maxLines = config.maxLines,
        overflow = config.overflow,
        softWrap = config.softWrap
    )
}