package io.everytech.poptracker.ui.components.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.everytech.poptracker.ui.components.everyapp.EveryIconButton
import io.everytech.poptracker.ui.components.everyapp.EveryIconButtonConfig
import io.everytech.poptracker.ui.components.everyapp.EveryIconButtonSize
import io.everytech.poptracker.ui.components.everyapp.EveryIconButtonStyle
import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PrimaryMarketplaceButton(
    marketplace: MarketplaceLink,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val size = ButtonDefaults.ExtraSmallContainerHeight
    ElevatedButton(
        onClick = onClick,
        modifier = modifier
            .heightIn(size)
            .fillMaxWidth(),
        contentPadding = ButtonDefaults.contentPaddingFor(size)
    ) {
//        Image(
//            painter = painterResource(marketplace.iconDrawableResource),
//            contentDescription = marketplace.name,
//            modifier = Modifier.width(64.dp)
//        )
        Text("Buy Now")
    }
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