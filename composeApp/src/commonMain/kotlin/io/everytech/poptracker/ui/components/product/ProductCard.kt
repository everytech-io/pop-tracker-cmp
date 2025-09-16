package io.everytech.poptracker.ui.components.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.everytech.poptracker.ui.components.everyapp.AspectRatios
import io.everytech.poptracker.ui.components.everyapp.EveryCard
import io.everytech.poptracker.ui.components.everyapp.EveryCardConfig
import io.everytech.poptracker.ui.components.everyapp.EveryIconButton
import io.everytech.poptracker.ui.components.everyapp.EveryIconButtonConfig
import io.everytech.poptracker.ui.components.everyapp.EveryIconButtonSize
import io.everytech.poptracker.ui.components.everyapp.EveryIconButtonStyle
import io.everytech.poptracker.ui.components.everyapp.EveryImageBox
import io.everytech.poptracker.ui.components.everyapp.EveryImageBoxConfig
import io.everytech.poptracker.ui.components.everyapp.EveryText
import io.everytech.poptracker.ui.components.everyapp.EveryTextConfig
import io.everytech.poptracker.ui.components.everyapp.EveryTextStyle
import io.everytech.poptracker.ui.theme.PopTrackerTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

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
            contentPadding = 0.dp,
            containerColor = MaterialTheme.colorScheme.surfaceBright
        ),
        onClick = onProductClick
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(config.verticalSpacing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Product Image with Bookmark button
            Box {
                EveryImageBox(
                    painter = painterResource(product.imageResource),
                    config = EveryImageBoxConfig(
                        aspectRatio = config.imageAspectRatio,
                        shape = RoundedCornerShape(config.imageCornerRadius),
                        contentDescription = product.name
                    )
                )
                
                // Bookmark button in upper right corner
                EveryIconButton(
                    painter = rememberVectorPainter(Icons.Outlined.BookmarkBorder),
                    onClick = { /* Handle bookmark */ },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp),
                    config = EveryIconButtonConfig(
                        style = EveryIconButtonStyle.FilledTonal,
                        size = EveryIconButtonSize.Small,
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                    )
                )
            }
            
            // Product Title
            EveryText(
                text = product.name,
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
                text = product.description,
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

@Preview
@Composable
fun ProductCardPreview() {
    PopTrackerTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            ProductCard(
                product = Product(
                    id = "preview-1",
                    name = "Labubu Halloween Keychain",
                    description = "Limited Edition Collectible",
                    imageName = "labubu_demo",
                    price = ProductPrice(amount = "15.99"),
                    marketplaces = listOf(
                        MarketplaceLink(
                            "Popmart",
                            iconName = "popmart",
                            type = MarketplaceType.Official,
                            url = "",
                            availability = AvailabilityStatus.InStock
                        ),
                        MarketplaceLink(
                            "Shopee",
                            iconName = "shopee",
                            type = MarketplaceType.Secondary,
                            url = "",
                            availability = AvailabilityStatus.OutOfStock
                        ),
                        MarketplaceLink(
                            "Lazada",
                            iconName = "lazada",
                            type = MarketplaceType.Secondary,
                            url = "",
                            availability = AvailabilityStatus.InStock
                        ),
                        MarketplaceLink(
                            "TikTok",
                            iconName = "tiktok",
                            type = MarketplaceType.Secondary,
                            url = "",
                            availability = AvailabilityStatus.InStock
                        ),
                    )
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}