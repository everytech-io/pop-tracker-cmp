package io.everytech.poptracker.ui.components.everyapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class EveryCardConfig(
    val shape: Shape = RoundedCornerShape(12.dp),
    val elevation: Dp = 0.dp,
    val containerColor: Color? = null,
    val contentColor: Color? = null,
    val borderStroke: BorderStroke? = null,
    val contentPadding: Dp = 0.dp
)

@Composable
fun EveryCard(
    modifier: Modifier = Modifier,
    config: EveryCardConfig = EveryCardConfig(),
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val cardModifier = if (config.contentPadding > 0.dp) {
        modifier
    } else {
        modifier
    }
    
    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = cardModifier,
            shape = config.shape,
            elevation = CardDefaults.cardElevation(defaultElevation = config.elevation),
            colors = CardDefaults.cardColors(
                containerColor = config.containerColor ?: MaterialTheme.colorScheme.surface,
                contentColor = config.contentColor ?: MaterialTheme.colorScheme.onSurface
            ),
            border = config.borderStroke
        ) {
            if (config.contentPadding > 0.dp) {
                androidx.compose.foundation.layout.Column(
                    modifier = Modifier.padding(config.contentPadding)
                ) {
                    content()
                }
            } else {
                content()
            }
        }
    } else {
        Card(
            modifier = cardModifier,
            shape = config.shape,
            elevation = CardDefaults.cardElevation(defaultElevation = config.elevation),
            colors = CardDefaults.cardColors(
                containerColor = config.containerColor ?: MaterialTheme.colorScheme.surface,
                contentColor = config.contentColor ?: MaterialTheme.colorScheme.onSurface
            ),
            border = config.borderStroke
        ) {
            if (config.contentPadding > 0.dp) {
                androidx.compose.foundation.layout.Column(
                    modifier = Modifier.padding(config.contentPadding)
                ) {
                    content()
                }
            } else {
                content()
            }
        }
    }
}