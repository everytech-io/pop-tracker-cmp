package io.everytech.poptracker.ui.components.everyapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.everytech.poptracker.ui.theme.PopTrackerTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class EveryButtonStyle {
    Primary,        // Filled button - high emphasis
    Secondary,      // Filled tonal button - medium emphasis  
    Elevated,       // Elevated button - medium emphasis with shadow
    Outlined,       // Outlined button - medium emphasis
    Text           // Text button - low emphasis
}

enum class EveryButtonSize(
    val horizontalPadding: Dp,
    val verticalPadding: Dp,
    val minHeight: Dp,
    val iconSize: Dp,
    val iconSpacing: Dp
) {
    ExtraSmall(12.dp, 4.dp, 24.dp, 16.dp, 4.dp),
    Small(16.dp, 6.dp, 32.dp, 16.dp, 6.dp),
    Medium(24.dp, 8.dp, 40.dp, 18.dp, 8.dp),
    Large(24.dp, 12.dp, 48.dp, 20.dp, 8.dp),
    ExtraLarge(24.dp, 16.dp, 56.dp, 24.dp, 8.dp)
}

sealed class IconPosition {
    data object Start : IconPosition()
    data object End : IconPosition()
}

sealed class ButtonIcon {
    data class Vector(val imageVector: ImageVector) : ButtonIcon()
    data class Painter(val painter: androidx.compose.ui.graphics.painter.Painter) : ButtonIcon()
}

@Immutable
data class EveryButtonConfig(
    val style: EveryButtonStyle = EveryButtonStyle.Primary,
    val size: EveryButtonSize = EveryButtonSize.Medium,
    val icon: ButtonIcon? = null,
    val iconPosition: IconPosition = IconPosition.Start,
    val enabled: Boolean = true,
    val fullWidth: Boolean = false,
    
    // Color customization
    val containerColor: Color? = null,
    val contentColor: Color? = null,
    val disabledContainerColor: Color? = null,
    val disabledContentColor: Color? = null,
    
    // Custom padding override
    val customContentPadding: PaddingValues? = null
) {
    val contentPadding: PaddingValues
        get() = customContentPadding ?: PaddingValues(
            horizontal = size.horizontalPadding,
            vertical = size.verticalPadding
        )
}

@Composable
fun EveryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    config: EveryButtonConfig = EveryButtonConfig()
) {
    val buttonModifier = if (config.fullWidth) {
        modifier.fillMaxWidth()
    } else {
        modifier
    }
    
    val colors = when (config.style) {
        EveryButtonStyle.Primary -> ButtonDefaults.buttonColors(
            containerColor = config.containerColor ?: MaterialTheme.colorScheme.primary,
            contentColor = config.contentColor ?: MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = config.disabledContainerColor 
                ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = config.disabledContentColor
                ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
        EveryButtonStyle.Secondary -> ButtonDefaults.filledTonalButtonColors(
            containerColor = config.containerColor ?: MaterialTheme.colorScheme.secondaryContainer,
            contentColor = config.contentColor ?: MaterialTheme.colorScheme.onSecondaryContainer,
            disabledContainerColor = config.disabledContainerColor
                ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = config.disabledContentColor
                ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
        EveryButtonStyle.Elevated -> ButtonDefaults.elevatedButtonColors(
            containerColor = config.containerColor ?: MaterialTheme.colorScheme.surface,
            contentColor = config.contentColor ?: MaterialTheme.colorScheme.primary,
            disabledContainerColor = config.disabledContainerColor
                ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = config.disabledContentColor
                ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
        EveryButtonStyle.Outlined -> ButtonDefaults.outlinedButtonColors(
            contentColor = config.contentColor ?: MaterialTheme.colorScheme.primary,
            disabledContentColor = config.disabledContentColor
                ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
        EveryButtonStyle.Text -> ButtonDefaults.textButtonColors(
            contentColor = config.contentColor ?: MaterialTheme.colorScheme.primary,
            disabledContentColor = config.disabledContentColor
                ?: MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
    }
    
    val buttonContent: @Composable () -> Unit = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (config.icon != null && config.iconPosition is IconPosition.Start) {
                ButtonIconContent(config.icon, config.size.iconSize)
                Spacer(Modifier.width(config.size.iconSpacing))
            }
            
            Text(text = text)
            
            if (config.icon != null && config.iconPosition is IconPosition.End) {
                Spacer(Modifier.width(config.size.iconSpacing))
                ButtonIconContent(config.icon, config.size.iconSize)
            }
        }
    }
    
    when (config.style) {
        EveryButtonStyle.Primary -> {
            Button(
                onClick = onClick,
                modifier = buttonModifier.height(config.size.minHeight),
                enabled = config.enabled,
                colors = colors,
                contentPadding = config.contentPadding
            ) {
                buttonContent()
            }
        }
        EveryButtonStyle.Secondary -> {
            FilledTonalButton(
                onClick = onClick,
                modifier = buttonModifier.height(config.size.minHeight),
                enabled = config.enabled,
                colors = colors,
                contentPadding = config.contentPadding
            ) {
                buttonContent()
            }
        }
        EveryButtonStyle.Elevated -> {
            ElevatedButton(
                onClick = onClick,
                modifier = buttonModifier.height(config.size.minHeight),
                enabled = config.enabled,
                colors = colors,
                elevation = ButtonDefaults.elevatedButtonElevation(),
                contentPadding = config.contentPadding
            ) {
                buttonContent()
            }
        }
        EveryButtonStyle.Outlined -> {
            OutlinedButton(
                onClick = onClick,
                modifier = buttonModifier.height(config.size.minHeight),
                enabled = config.enabled,
                colors = colors,
                border = ButtonDefaults.outlinedButtonBorder(enabled = config.enabled),
                contentPadding = config.contentPadding
            ) {
                buttonContent()
            }
        }
        EveryButtonStyle.Text -> {
            TextButton(
                onClick = onClick,
                modifier = buttonModifier.height(config.size.minHeight),
                enabled = config.enabled,
                colors = colors,
                contentPadding = config.contentPadding
            ) {
                buttonContent()
            }
        }
    }
}

@Composable
private fun ButtonIconContent(icon: ButtonIcon, size: Dp) {
    when (icon) {
        is ButtonIcon.Vector -> {
            Icon(
                imageVector = icon.imageVector,
                contentDescription = null,
                modifier = Modifier.size(size)
            )
        }
        is ButtonIcon.Painter -> {
            Icon(
                painter = icon.painter,
                contentDescription = null,
                modifier = Modifier.size(size)
            )
        }
    }
}

@Preview
@Composable
fun EveryButtonStylesPreview() {
    PopTrackerTheme {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Button Styles",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                // Primary
                EveryButton(
                    text = "Primary Button",
                    onClick = { },
                    config = EveryButtonConfig(
                        style = EveryButtonStyle.Primary
                    )
                )
                
                // Secondary (Filled Tonal)
                EveryButton(
                    text = "Secondary Button",
                    onClick = { },
                    config = EveryButtonConfig(
                        style = EveryButtonStyle.Secondary
                    )
                )
                
                // Elevated
                EveryButton(
                    text = "Elevated Button",
                    onClick = { },
                    config = EveryButtonConfig(
                        style = EveryButtonStyle.Elevated
                    )
                )
                
                // Outlined
                EveryButton(
                    text = "Outlined Button",
                    onClick = { },
                    config = EveryButtonConfig(
                        style = EveryButtonStyle.Outlined
                    )
                )
                
                // Text
                EveryButton(
                    text = "Text Button",
                    onClick = { },
                    config = EveryButtonConfig(
                        style = EveryButtonStyle.Text
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun EveryButtonSizesPreview() {
    PopTrackerTheme {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Button Sizes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        EveryButton(
                            text = "XS",
                            onClick = { },
                            config = EveryButtonConfig(
                                size = EveryButtonSize.ExtraSmall
                            )
                        )
                        
                        EveryButton(
                            text = "Small",
                            onClick = { },
                            config = EveryButtonConfig(
                                size = EveryButtonSize.Small
                            )
                        )
                        
                        EveryButton(
                            text = "Medium",
                            onClick = { },
                            config = EveryButtonConfig(
                                size = EveryButtonSize.Medium
                            )
                        )
                    }
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        EveryButton(
                            text = "Large",
                            onClick = { },
                            config = EveryButtonConfig(
                                size = EveryButtonSize.Large
                            )
                        )
                        
                        EveryButton(
                            text = "Extra Large",
                            onClick = { },
                            config = EveryButtonConfig(
                                size = EveryButtonSize.ExtraLarge
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun EveryButtonWithIconsPreview() {
    PopTrackerTheme {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Buttons with Icons",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                // Icon at start
                EveryButton(
                    text = "Add to Cart",
                    onClick = { },
                    config = EveryButtonConfig(
                        iconPosition = IconPosition.Start
                    )
                )
                
                // Icon at end
                EveryButton(
                    text = "Add Item",
                    onClick = { },
                    config = EveryButtonConfig(
                        iconPosition = IconPosition.End,
                        style = EveryButtonStyle.Secondary
                    )
                )
                
                // Outlined with icon
                EveryButton(
                    text = "Favorite",
                    onClick = { },
                    config = EveryButtonConfig(
                        style = EveryButtonStyle.Outlined
                    )
                )
                
                // Text button with icon
                EveryButton(
                    text = "Learn More",
                    onClick = { },
                    config = EveryButtonConfig(
                        style = EveryButtonStyle.Text,
                        iconPosition = IconPosition.End
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun EveryButtonStatesPreview() {
    PopTrackerTheme {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "Button States",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                // Enabled vs Disabled for each style
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Primary",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        EveryButton(
                            text = "Enabled",
                            onClick = { },
                            config = EveryButtonConfig(
                                style = EveryButtonStyle.Primary,
                                enabled = true
                            )
                        )
                        EveryButton(
                            text = "Disabled",
                            onClick = { },
                            config = EveryButtonConfig(
                                style = EveryButtonStyle.Primary,
                                enabled = false
                            )
                        )
                    }
                    
                    Text(
                        text = "Secondary",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        EveryButton(
                            text = "Enabled",
                            onClick = { },
                            config = EveryButtonConfig(
                                style = EveryButtonStyle.Secondary,
                                enabled = true
                            )
                        )
                        EveryButton(
                            text = "Disabled",
                            onClick = { },
                            config = EveryButtonConfig(
                                style = EveryButtonStyle.Secondary,
                                enabled = false
                            )
                        )
                    }
                    
                    Text(
                        text = "Outlined",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        EveryButton(
                            text = "Enabled",
                            onClick = { },
                            config = EveryButtonConfig(
                                style = EveryButtonStyle.Outlined,
                                enabled = true
                            )
                        )
                        EveryButton(
                            text = "Disabled",
                            onClick = { },
                            config = EveryButtonConfig(
                                style = EveryButtonStyle.Outlined,
                                enabled = false
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun EveryButtonFullWidthPreview() {
    PopTrackerTheme {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Full Width Buttons",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                EveryButton(
                    text = "Full Width Primary",
                    onClick = { },
                    config = EveryButtonConfig(
                        fullWidth = true,
                        style = EveryButtonStyle.Primary
                    )
                )
                
                EveryButton(
                    text = "Full Width Secondary",
                    onClick = { },
                    config = EveryButtonConfig(
                        fullWidth = true,
                        style = EveryButtonStyle.Secondary
                    )
                )
                
                EveryButton(
                    text = "Full Width Outlined",
                    onClick = { },
                    config = EveryButtonConfig(
                        fullWidth = true,
                        style = EveryButtonStyle.Outlined
                    )
                )
                
                EveryButton(
                    text = "Full Width with Icon",
                    onClick = { },
                    config = EveryButtonConfig(
                        fullWidth = true,
                        style = EveryButtonStyle.Elevated
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun EveryButtonCustomColorsPreview() {
    PopTrackerTheme {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Custom Colors",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                EveryButton(
                    text = "Custom Primary",
                    onClick = { },
                    config = EveryButtonConfig(
                        style = EveryButtonStyle.Primary,
                        containerColor = Color(0xFF6200EA),
                        contentColor = Color.White
                    )
                )
                
                EveryButton(
                    text = "Custom Secondary",
                    onClick = { },
                    config = EveryButtonConfig(
                        style = EveryButtonStyle.Secondary,
                        containerColor = Color(0xFFE1BEE7),
                        contentColor = Color(0xFF4A148C)
                    )
                )
                
                EveryButton(
                    text = "Custom Outlined",
                    onClick = { },
                    config = EveryButtonConfig(
                        style = EveryButtonStyle.Outlined,
                        contentColor = Color(0xFFFF5722)
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun EveryButtonComprehensiveSizingPreview() {
    PopTrackerTheme {
        Surface(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "Button Sizing Guide - All Styles & Sizes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                // Primary Style with all sizes
                ButtonStyleSection(
                    title = "Primary (Filled)",
                    style = EveryButtonStyle.Primary
                )
                
                // Secondary Style with all sizes
                ButtonStyleSection(
                    title = "Secondary (Filled Tonal)",
                    style = EveryButtonStyle.Secondary
                )
                
                // Elevated Style with all sizes
                ButtonStyleSection(
                    title = "Elevated",
                    style = EveryButtonStyle.Elevated
                )
                
                // Outlined Style with all sizes
                ButtonStyleSection(
                    title = "Outlined",
                    style = EveryButtonStyle.Outlined
                )
                
                // Text Style with all sizes
                ButtonStyleSection(
                    title = "Text",
                    style = EveryButtonStyle.Text
                )
            }
        }
    }
}

@Composable
private fun ButtonStyleSection(
    title: String,
    style: EveryButtonStyle
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        
        // Text only buttons
        Text(
            text = "Text Only",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EveryButtonSize.entries.forEach { size ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    EveryButton(
                        text = getSizeLabel(size),
                        onClick = { },
                        config = EveryButtonConfig(
                            style = style,
                            size = size
                        )
                    )
                    Text(
                        text = "${size.minHeight}dp",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        // Icon on left
        Text(
            text = "Icon Left",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EveryButtonSize.entries.forEach { size ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    EveryButton(
                        text = getSizeLabel(size),
                        onClick = { },
                        config = EveryButtonConfig(
                            style = style,
                            size = size,
                            icon = ButtonIcon.Vector(Icons.Default.Add),
                            iconPosition = IconPosition.Start
                        )
                    )
                    Text(
                        text = "${size.minHeight}dp",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        // Icon on right
        Text(
            text = "Icon Right",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EveryButtonSize.entries.forEach { size ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    EveryButton(
                        text = getSizeLabel(size),
                        onClick = { },
                        config = EveryButtonConfig(
                            style = style,
                            size = size,
                            icon = ButtonIcon.Vector(Icons.Default.ShoppingCart),
                            iconPosition = IconPosition.End
                        )
                    )
                    Text(
                        text = "${size.minHeight}dp",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun getSizeLabel(size: EveryButtonSize): String {
    return when (size) {
        EveryButtonSize.ExtraSmall -> "XS"
        EveryButtonSize.Small -> "S"
        EveryButtonSize.Medium -> "M"
        EveryButtonSize.Large -> "L"
        EveryButtonSize.ExtraLarge -> "XL"
    }
}