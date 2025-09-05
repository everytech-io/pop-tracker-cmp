package io.everytech.poptracker.ui.components.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.everytech.poptracker.ui.components.everyapp.EveryButton
import io.everytech.poptracker.ui.components.everyapp.EveryButtonConfig
import io.everytech.poptracker.ui.components.everyapp.EveryButtonSize
import io.everytech.poptracker.ui.components.everyapp.EveryButtonStyle
import io.everytech.poptracker.ui.components.everyapp.EveryIconButton
import io.everytech.poptracker.ui.components.everyapp.EveryIconButtonConfig
import io.everytech.poptracker.ui.components.everyapp.EveryIconButtonSize
import io.everytech.poptracker.ui.components.everyapp.EveryIconButtonStyle
import io.everytech.poptracker.ui.components.everyapp.ButtonIcon
import io.everytech.poptracker.ui.components.everyapp.IconPosition
import org.jetbrains.compose.resources.painterResource

@Composable
fun PrimaryMarketplaceButton(
    marketplace: MarketplaceLink,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    EveryButton(
        text = "Buy Now",
        onClick = onClick,
        modifier = modifier,
        config = EveryButtonConfig(
            style = EveryButtonStyle.Elevated,
            size = EveryButtonSize.Medium,
            icon = ButtonIcon.Vector(Icons.Default.ShoppingCart),
            iconPosition = IconPosition.Start,
            fullWidth = true,
            enabled = marketplace.availability == AvailabilityStatus.InStock
        )
    )
}

@Composable
fun MarketplaceButtonRow(
    marketplaces: List<MarketplaceLink>,
    modifier: Modifier = Modifier,
    onMarketplaceClick: (MarketplaceLink) -> Unit
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 4.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalArrangement = Arrangement.spacedBy(space = 4.dp)
    ) {
        marketplaces.forEach { link ->
            EveryIconButton(
                painter = painterResource(link.iconDrawableResource),
                onClick = { onMarketplaceClick(link) },
                config = EveryIconButtonConfig(
                    style = EveryIconButtonStyle.FilledTonal,
                    enabled = link.availability == AvailabilityStatus.InStock,
                    size = EveryIconButtonSize.ExtraSmall,
                    tooltipText = link.name
                )
            )
        }
    }
}