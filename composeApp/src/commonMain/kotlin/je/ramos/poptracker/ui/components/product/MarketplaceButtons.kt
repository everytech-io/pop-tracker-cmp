package je.ramos.poptracker.ui.components.product

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
import je.ramos.poptracker.ui.components.everyapp.EveryIconButton
import je.ramos.poptracker.ui.components.everyapp.EveryIconButtonConfig
import je.ramos.poptracker.ui.components.everyapp.EveryIconButtonSize
import je.ramos.poptracker.ui.components.everyapp.EveryIconButtonStyle
import androidx.compose.foundation.Image

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
        Image(
            painter = marketplace.marketplace.icon,
            contentDescription = marketplace.marketplace.name,
            modifier = Modifier.width(64.dp)
        )
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
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        marketplaces.forEach { link ->
            EveryIconButton(
                painter = link.marketplace.icon,
                onClick = { onMarketplaceClick(link) },
                config = EveryIconButtonConfig(
                    style = EveryIconButtonStyle.FilledTonal,
                    size = EveryIconButtonSize.Medium,
                    tooltipText = link.marketplace.name
                )
            )
        }
    }
}