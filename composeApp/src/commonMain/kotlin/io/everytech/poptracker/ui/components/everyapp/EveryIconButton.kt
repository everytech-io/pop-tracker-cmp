package io.everytech.poptracker.ui.components.everyapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.everytech.poptracker.ui.theme.PopTrackerTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import poptracker.composeapp.generated.resources.Res
import poptracker.composeapp.generated.resources.icon_open_link
import poptracker.composeapp.generated.resources.popmart

enum class EveryIconButtonStyle {
    Standard,
    Filled,
    FilledTonal,
    Outlined
}

enum class EveryIconButtonSize(val containerSize: Dp, val iconSize: Dp) {
    ExtraSmall(32.dp, 16.dp),
    Small(40.dp, 20.dp),
    Medium(48.dp, 24.dp),
    Large(56.dp, 28.dp),
    ExtraLarge(64.dp, 32.dp)
}

@Immutable
data class EveryIconButtonConfig(
    val style: EveryIconButtonStyle = EveryIconButtonStyle.Standard,
    val size: EveryIconButtonSize = EveryIconButtonSize.Medium,
    val customSize: Dp? = null, // For custom sizes, overrides size enum
    val customIconSize: Dp? = null, // For custom icon sizes, overrides size enum
    val containerColor: Color? = null,
    val contentColor: Color? = null,
    val iconTint: Color? = null, // Tint color for the icon, null means no tinting
    val tooltipText: String? = null,
    val enabled: Boolean = true
) {
    // Computed properties for actual sizes
    val actualContainerSize: Dp get() = customSize ?: size.containerSize
    val actualIconSize: Dp get() = customIconSize ?: size.iconSize
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EveryIconButton(
    painter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    config: EveryIconButtonConfig = EveryIconButtonConfig()
) {
    val iconButton = @Composable {
        when (config.style) {
            EveryIconButtonStyle.Standard -> {
                IconButton(
                    onClick = onClick,
                    modifier = modifier.size(config.actualContainerSize),
                    enabled = config.enabled,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = config.containerColor ?: Color.Transparent,
                        contentColor = config.contentColor ?: MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Image(
                        painter = painter,
                        contentDescription = config.tooltipText,
                        modifier = Modifier.size(config.actualIconSize),
                        colorFilter = when {
                            !config.enabled -> ColorFilter.tint(Color.Gray)
                            config.iconTint != null -> ColorFilter.tint(config.iconTint)
                            else -> null
                        }
                    )
                }
            }
            EveryIconButtonStyle.Filled -> {
                FilledIconButton(
                    onClick = onClick,
                    modifier = modifier.size(config.actualContainerSize),
                    enabled = config.enabled,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = config.containerColor ?: MaterialTheme.colorScheme.primary,
                        contentColor = config.contentColor ?: MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Image(
                        painter = painter,
                        contentDescription = config.tooltipText,
                        modifier = Modifier.size(config.actualIconSize),
                        colorFilter = when {
                            !config.enabled -> ColorFilter.tint(Color.Gray)
                            config.iconTint != null -> ColorFilter.tint(config.iconTint)
                            else -> null
                        }
                    )
                }
            }
            EveryIconButtonStyle.FilledTonal -> {
                FilledTonalIconButton(
                    onClick = onClick,
                    modifier = modifier.size(config.actualContainerSize),
                    enabled = config.enabled,
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = config.containerColor ?: MaterialTheme.colorScheme.primaryContainer,
                        contentColor = config.contentColor ?: MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Image(
                        painter = painter,
                        contentDescription = config.tooltipText,
                        modifier = Modifier.size(config.actualIconSize),
                        colorFilter = when {
                            !config.enabled -> ColorFilter.tint(Color.Gray)
                            config.iconTint != null -> ColorFilter.tint(config.iconTint)
                            else -> null
                        }
                    )
                }
            }
            EveryIconButtonStyle.Outlined -> {
                OutlinedIconButton(
                    onClick = onClick,
                    modifier = modifier.size(config.actualContainerSize),
                    enabled = config.enabled,
                    colors = IconButtonDefaults.outlinedIconButtonColors(
                        contentColor = config.contentColor ?: MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Image(
                        painter = painter,
                        contentDescription = config.tooltipText,
                        modifier = Modifier.size(config.actualIconSize),
                        colorFilter = when {
                            !config.enabled -> ColorFilter.tint(Color.Gray)
                            config.iconTint != null -> ColorFilter.tint(config.iconTint)
                            else -> null
                        }
                    )
                }
            }
        }
    }
    
    if (config.tooltipText != null) {
        TooltipBox(
            positionProvider = TooltipDefaults.rememberTooltipPositionProvider(),
            tooltip = { PlainTooltip { Text(config.tooltipText) } },
            state = rememberTooltipState()
        ) {
            iconButton()
        }
    } else {
        iconButton()
    }
}

@Preview
@Composable
fun EveryIconButtonSizesPreview() {
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
                    text = "Icon Button Sizes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                // Show all sizes
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        EveryIconButton(
                            painter = painterResource(Res.drawable.icon_open_link),
                            onClick = { },
                            config = EveryIconButtonConfig(
                                size = EveryIconButtonSize.ExtraSmall,
                                style = EveryIconButtonStyle.FilledTonal
                            )
                        )
                        Text("XS", style = MaterialTheme.typography.labelSmall)
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        EveryIconButton(
                            painter = painterResource(Res.drawable.icon_open_link),
                            onClick = { },
                            config = EveryIconButtonConfig(
                                size = EveryIconButtonSize.Small,
                                style = EveryIconButtonStyle.FilledTonal
                            )
                        )
                        Text("S", style = MaterialTheme.typography.labelSmall)
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        EveryIconButton(
                            painter = painterResource(Res.drawable.icon_open_link),
                            onClick = { },
                            config = EveryIconButtonConfig(
                                size = EveryIconButtonSize.Medium,
                                style = EveryIconButtonStyle.FilledTonal
                            )
                        )
                        Text("M", style = MaterialTheme.typography.labelSmall)
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        EveryIconButton(
                            painter = painterResource(Res.drawable.icon_open_link),
                            onClick = { },
                            config = EveryIconButtonConfig(
                                size = EveryIconButtonSize.Large,
                                style = EveryIconButtonStyle.FilledTonal
                            )
                        )
                        Text("L", style = MaterialTheme.typography.labelSmall)
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        EveryIconButton(
                            painter = painterResource(Res.drawable.icon_open_link),
                            onClick = { },
                            config = EveryIconButtonConfig(
                                size = EveryIconButtonSize.ExtraLarge,
                                style = EveryIconButtonStyle.FilledTonal
                            )
                        )
                        Text("XL", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun EveryIconButtonStylesPreview() {
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
                    text = "Icon Button Styles",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                // Show all styles with Medium size
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        EveryIconButton(
                            painter = painterResource(Res.drawable.popmart),
                            onClick = { },
                            config = EveryIconButtonConfig(
                                size = EveryIconButtonSize.Medium,
                                style = EveryIconButtonStyle.Standard,
                                tooltipText = "Standard"
                            )
                        )
                        Text("Standard", style = MaterialTheme.typography.labelSmall)
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        EveryIconButton(
                            painter = painterResource(Res.drawable.popmart),
                            onClick = { },
                            config = EveryIconButtonConfig(
                                size = EveryIconButtonSize.Medium,
                                style = EveryIconButtonStyle.Filled,
                                tooltipText = "Filled"
                            )
                        )
                        Text("Filled", style = MaterialTheme.typography.labelSmall)
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        EveryIconButton(
                            painter = painterResource(Res.drawable.popmart),
                            onClick = { },
                            config = EveryIconButtonConfig(
                                size = EveryIconButtonSize.Medium,
                                style = EveryIconButtonStyle.FilledTonal,
                                tooltipText = "FilledTonal"
                            )
                        )
                        Text("Tonal", style = MaterialTheme.typography.labelSmall)
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        EveryIconButton(
                            painter = painterResource(Res.drawable.popmart),
                            onClick = { },
                            config = EveryIconButtonConfig(
                                size = EveryIconButtonSize.Medium,
                                style = EveryIconButtonStyle.Outlined,
                                tooltipText = "Outlined"
                            )
                        )
                        Text("Outlined", style = MaterialTheme.typography.labelSmall)
                    }
                }
                
                Text(
                    text = "Custom Size Example",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    EveryIconButton(
                        painter = painterResource(Res.drawable.popmart),
                        onClick = { },
                        config = EveryIconButtonConfig(
                            size = EveryIconButtonSize.Medium,
                            customSize = 72.dp,
                            customIconSize = 36.dp,
                            style = EveryIconButtonStyle.FilledTonal,
                            tooltipText = "Custom 72dp button with 36dp icon"
                        )
                    )
                    Text(
                        text = "Custom: 72dp container, 36dp icon",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                Text(
                    text = "Icon Tinting Examples",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        EveryIconButton(
                            painter = painterResource(Res.drawable.popmart),
                            onClick = { },
                            config = EveryIconButtonConfig(
                                size = EveryIconButtonSize.Medium,
                                style = EveryIconButtonStyle.Standard,
                                iconTint = null, // No tinting - original colors
                                tooltipText = "No tint"
                            )
                        )
                        Text("No Tint", style = MaterialTheme.typography.labelSmall)
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        EveryIconButton(
                            painter = painterResource(Res.drawable.popmart),
                            onClick = { },
                            config = EveryIconButtonConfig(
                                size = EveryIconButtonSize.Medium,
                                style = EveryIconButtonStyle.Standard,
                                iconTint = MaterialTheme.colorScheme.primary,
                                tooltipText = "Primary tint"
                            )
                        )
                        Text("Primary", style = MaterialTheme.typography.labelSmall)
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        EveryIconButton(
                            painter = painterResource(Res.drawable.popmart),
                            onClick = { },
                            config = EveryIconButtonConfig(
                                size = EveryIconButtonSize.Medium,
                                style = EveryIconButtonStyle.Standard,
                                iconTint = Color.Red,
                                tooltipText = "Red tint"
                            )
                        )
                        Text("Red", style = MaterialTheme.typography.labelSmall)
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        EveryIconButton(
                            painter = painterResource(Res.drawable.popmart),
                            onClick = { },
                            config = EveryIconButtonConfig(
                                size = EveryIconButtonSize.Medium,
                                style = EveryIconButtonStyle.Standard,
                                iconTint = Color.Green,
                                tooltipText = "Green tint"
                            )
                        )
                        Text("Green", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun EveryIconButtonDisabledStatesPreview() {
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
                    text = "Disabled Button States",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = "Enabled vs Disabled",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                
                // Show enabled vs disabled for each style
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Standard
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Standard:",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(0.3f)
                        )
                        
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            EveryIconButton(
                                painter = painterResource(Res.drawable.icon_open_link),
                                onClick = { },
                                config = EveryIconButtonConfig(
                                    style = EveryIconButtonStyle.Standard,
                                    enabled = true,
                                    tooltipText = "Enabled Standard"
                                )
                            )
                            Text("Enabled", style = MaterialTheme.typography.labelSmall)
                        }
                        
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            EveryIconButton(
                                painter = painterResource(Res.drawable.icon_open_link),
                                onClick = { },
                                config = EveryIconButtonConfig(
                                    style = EveryIconButtonStyle.Standard,
                                    enabled = false,
                                    tooltipText = "Disabled Standard"
                                )
                            )
                            Text("Disabled", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        }
                    }
                    
                    // Filled
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Filled:",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(0.3f)
                        )
                        
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            EveryIconButton(
                                painter = painterResource(Res.drawable.icon_open_link),
                                onClick = { },
                                config = EveryIconButtonConfig(
                                    style = EveryIconButtonStyle.Filled,
                                    enabled = true,
                                    tooltipText = "Enabled Filled"
                                )
                            )
                            Text("Enabled", style = MaterialTheme.typography.labelSmall)
                        }
                        
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            EveryIconButton(
                                painter = painterResource(Res.drawable.icon_open_link),
                                onClick = { },
                                config = EveryIconButtonConfig(
                                    style = EveryIconButtonStyle.Filled,
                                    enabled = false,
                                    tooltipText = "Disabled Filled"
                                )
                            )
                            Text("Disabled", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        }
                    }
                    
                    // FilledTonal
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Filled Tonal:",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(0.3f)
                        )
                        
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            EveryIconButton(
                                painter = painterResource(Res.drawable.icon_open_link),
                                onClick = { },
                                config = EveryIconButtonConfig(
                                    style = EveryIconButtonStyle.FilledTonal,
                                    enabled = true,
                                    tooltipText = "Enabled Filled Tonal"
                                )
                            )
                            Text("Enabled", style = MaterialTheme.typography.labelSmall)
                        }
                        
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            EveryIconButton(
                                painter = painterResource(Res.drawable.icon_open_link),
                                onClick = { },
                                config = EveryIconButtonConfig(
                                    style = EveryIconButtonStyle.FilledTonal,
                                    enabled = false,
                                    tooltipText = "Disabled Filled Tonal"
                                )
                            )
                            Text("Disabled", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        }
                    }
                    
                    // Outlined
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Outlined:",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(0.3f)
                        )
                        
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            EveryIconButton(
                                painter = painterResource(Res.drawable.icon_open_link),
                                onClick = { },
                                config = EveryIconButtonConfig(
                                    style = EveryIconButtonStyle.Outlined,
                                    enabled = true,
                                    tooltipText = "Enabled Outlined"
                                )
                            )
                            Text("Enabled", style = MaterialTheme.typography.labelSmall)
                        }
                        
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            EveryIconButton(
                                painter = painterResource(Res.drawable.icon_open_link),
                                onClick = { },
                                config = EveryIconButtonConfig(
                                    style = EveryIconButtonStyle.Outlined,
                                    enabled = false,
                                    tooltipText = "Disabled Outlined"
                                )
                            )
                            Text("Disabled", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        }
                    }
                }
                
                Text(
                    text = "Custom Tinted Icons (Disabled state overrides custom tint)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        EveryIconButton(
                            painter = painterResource(Res.drawable.popmart),
                            onClick = { },
                            config = EveryIconButtonConfig(
                                style = EveryIconButtonStyle.Standard,
                                iconTint = MaterialTheme.colorScheme.primary,
                                enabled = true
                            )
                        )
                        Text("Primary Tint", style = MaterialTheme.typography.labelSmall)
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        EveryIconButton(
                            painter = painterResource(Res.drawable.popmart),
                            onClick = { },
                            config = EveryIconButtonConfig(
                                style = EveryIconButtonStyle.Standard,
                                iconTint = MaterialTheme.colorScheme.primary,
                                enabled = false
                            )
                        )
                        Text("Disabled (Gray)", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    }
                }
            }
        }
    }
}