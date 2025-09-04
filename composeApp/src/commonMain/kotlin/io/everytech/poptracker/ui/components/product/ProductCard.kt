package io.everytech.poptracker.ui.components.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.everytech.poptracker.ui.components.everyapp.AspectRatios
import io.everytech.poptracker.ui.components.everyapp.EveryCard
import io.everytech.poptracker.ui.components.everyapp.EveryCardConfig
import io.everytech.poptracker.ui.components.everyapp.EveryImageBox
import io.everytech.poptracker.ui.components.everyapp.EveryImageBoxConfig
import io.everytech.poptracker.ui.components.everyapp.EveryText
import io.everytech.poptracker.ui.components.everyapp.EveryTextConfig
import io.everytech.poptracker.ui.components.everyapp.EveryTextStyle
import org.jetbrains.compose.resources.painterResource

@Immutable
data class ProductCardConfig(
    val imageAspectRatio: Float = AspectRatios.SQUARE,
    val imageCornerRadius: Dp = 0.dp,
    val showDivider: Boolean = true,
    val horizontalPadding: Dp = 8.dp,
    val verticalSpacing: Dp = 4.dp,
    val cardElevation: Dp = 0.dp,
    val cardCornerRadius: Dp = 12.dp
)

@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    config: ProductCardConfig = ProductCardConfig(),
    onProductClick: () -> Unit = {},
    onMarketplaceClick: (MarketplaceLink) -> Unit = {}
) {
    EveryCard(
        modifier = modifier.fillMaxWidth(),
        config = EveryCardConfig(
            shape = RoundedCornerShape(config.cardCornerRadius),
            elevation = config.cardElevation,
            contentPadding = 0.dp
        ),
        onClick = onProductClick
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(config.verticalSpacing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Product Image
            EveryImageBox(
                painter = painterResource(product.imageDrawableResource),
                config = EveryImageBoxConfig(
                    aspectRatio = config.imageAspectRatio,
                    shape = RoundedCornerShape(config.imageCornerRadius),
                    contentDescription = product.title
                )
            )
            
            // Product Title
            EveryText(
                text = product.title,
                modifier = Modifier
                    .padding(horizontal = config.horizontalPadding)
                    .fillMaxWidth(),
                config = EveryTextConfig(
                    style = EveryTextStyle.TitleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            )
            
            // Product Subtitle
            EveryText(
                text = product.subtitle,
                modifier = Modifier
                    .padding(horizontal = config.horizontalPadding)
                    .fillMaxWidth(),
                config = EveryTextConfig(
                    style = EveryTextStyle.BodySmall,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            )
            
            // Primary Marketplace (Official/Main)
            product.marketplaces.firstOrNull { it.type == MarketplaceType.Official }?.let { link ->
                Box(
                    modifier = Modifier.padding(horizontal = config.horizontalPadding)
                ) {
                    PrimaryMarketplaceButton(
                        marketplace = link,
                        onClick = { onMarketplaceClick(link) }
                    )
                }
            }
            
            // Divider
            if (config.showDivider && product.marketplaces.any { it.type == MarketplaceType.Secondary }) {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = config.horizontalPadding)
                )
            }
            
            // Secondary Marketplaces
            val secondaryMarketplaces = product.marketplaces.filter { 
                it.type == MarketplaceType.Secondary
            }
            if (secondaryMarketplaces.isNotEmpty()) {
                MarketplaceButtonRow(
                    marketplaces = secondaryMarketplaces,
                    onMarketplaceClick = onMarketplaceClick,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}