package je.ramos.poptracker.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import je.ramos.poptracker.ui.theme.PopTrackerTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import poptracker.composeapp.generated.resources.Res
import poptracker.composeapp.generated.resources.amazon
import poptracker.composeapp.generated.resources.compose_multiplatform
import poptracker.composeapp.generated.resources.icon_open_link
import poptracker.composeapp.generated.resources.labubu_demo
import poptracker.composeapp.generated.resources.lazada
import poptracker.composeapp.generated.resources.popmart
import poptracker.composeapp.generated.resources.shopee
import poptracker.composeapp.generated.resources.tiktok

@Immutable
data class ProductLink(
    val title: String,
    val subtitle: String,
    val websiteName: String,
    val websiteIcon: Painter,
    val websiteDomain: WebsiteDomain,
    val price: String,
    val currency: String = "$",
    val availability: AvailabilityStatus = AvailabilityStatus.InStock
)

enum class WebsiteDomain {
    Amazon,
    Popmart,
    Tiktok,
    Shopee,
    Lazada
}

enum class AvailabilityStatus(val label: String, val color: Color) {
    InStock("In Stock", Color(0xFF4CAF50)),
    OutOfStock("Out of Stock", Color(0xFFF44336)),
    ComingSoon("Coming Soon", Color(0xFFFF9800)),
    Limited("Limited", Color(0xFFE91E63))
}

@Immutable
data class ProductLinkCardConfig(
    val cardElevation: androidx.compose.ui.unit.Dp = 8.dp,
    val overlayGradient: Boolean = true
)

/**
 * Product card with horizontal layout and organized link buttons.
 *
 * Features a horizontal layout with product image on the left, product info on the right,
 * followed by official link buttons and marketplace link buttons.
 *
 * @param productImage Painter for the main product image
 * @param links List of website links with prices for this product
 * @param modifier Modifier for the card
 * @param config Configuration for card appearance and image settings
 * @param officialLink Optional URL for the official POP MART link. When null, the official link section is hidden
 * @param officialLinkAvailability Availability status for the official link (default: InStock)
 * @param onOfficialLinkClick Callback invoked when the official link button is clicked
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ProductLinkCard(
    productImage: Painter,
    links: List<ProductLink>,
    modifier: Modifier = Modifier,
    config: ProductLinkCardConfig = ProductLinkCardConfig(),
    officialLink: String? = null,
    officialLinkAvailability: AvailabilityStatus = AvailabilityStatus.InStock,
    onOfficialLinkClick: ((String) -> Unit)? = null
) {
    val firstLink = links.firstOrNull()
    
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = config.cardElevation),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top section: Image on left, product info on right
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Product image
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Image(
                        painter = productImage,
                        contentDescription = firstLink?.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                
                // Product info
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    firstLink?.let { link ->
                        Text(
                            text = link.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = link.subtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${link.currency}${link.price}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            
            // Official links section
            if (officialLink != null) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Official Link",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    OfficialPopmartButton(
                        onClick = {
                            onOfficialLinkClick?.invoke(officialLink)
                        }
                    )
                }
            }
            
            // Marketplace links section
            val marketplaceLinks = links.filter { 
                it.websiteDomain in listOf(WebsiteDomain.Shopee, WebsiteDomain.Lazada, WebsiteDomain.Tiktok)
            }
            
            if (marketplaceLinks.isNotEmpty()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Available on Marketplaces",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    marketplaceLinks.forEach { link ->
                        MarketplaceLinkButton(link = link)
                    }
                }
            }
        }
    }
}


@Composable
private fun OfficialPopmartButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            color = MaterialTheme.colorScheme.onPrimary,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.popmart),
                        contentDescription = "POP MART",
                        modifier = Modifier.size(16.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                Text(text = "POP MART")
            }
            
            Icon(
                painter = painterResource(Res.drawable.icon_open_link),
                contentDescription = "Open link",
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun MarketplaceLinkButton(
    link: ProductLink,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = link.websiteIcon,
                        contentDescription = link.websiteName,
                        modifier = Modifier.size(16.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                Text(text = link.websiteName)
            }
            
            Icon(
                painter = painterResource(Res.drawable.icon_open_link),
                contentDescription = "Open link",
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

// Preview Section
@Preview
@Composable
fun ProductLinkCardPreview() {
    PopTrackerTheme {
        val sampleLinks = listOf(
            ProductLink(
                title = "Labubu Halloween Keychain",
                subtitle = "Limited Edition Collectible",
                websiteName = "PopMart",
                websiteIcon = painterResource(Res.drawable.popmart),
                websiteDomain = WebsiteDomain.Popmart,
                price = "15.99",
                availability = AvailabilityStatus.InStock
            ),
            ProductLink(
                title = "Labubu Halloween Keychain",
                subtitle = "Limited Edition Collectible",
                websiteName = "Amazon",
                websiteIcon = painterResource(Res.drawable.amazon),
                websiteDomain = WebsiteDomain.Amazon,
                price = "18.50",
                availability = AvailabilityStatus.InStock
            ),
            ProductLink(
                title = "Labubu Halloween Keychain",
                subtitle = "Limited Edition Collectible",
                websiteName = "TikTok",
                websiteIcon = painterResource(Res.drawable.tiktok),
                websiteDomain = WebsiteDomain.Tiktok,
                price = "22.00",
                availability = AvailabilityStatus.Limited
            ),
            ProductLink(
                title = "Labubu Halloween Keychain",
                subtitle = "Limited Edition Collectible",
                websiteName = "Shopee",
                websiteIcon = painterResource(Res.drawable.shopee),
                websiteDomain = WebsiteDomain.Shopee,
                price = "22.00",
                availability = AvailabilityStatus.Limited
            ),
            ProductLink(
                title = "Labubu Halloween Keychain",
                subtitle = "Limited Edition Collectible",
                websiteName = "Shopee",
                websiteIcon = painterResource(Res.drawable.lazada),
                websiteDomain = WebsiteDomain.Lazada,
                price = "22.00",
                availability = AvailabilityStatus.Limited
            )
        )

        ProductLinkCard(
            productImage = painterResource(Res.drawable.labubu_demo),
            links = sampleLinks,
            officialLink = "https://www.popmart.com/us/products/labubu-halloween",
            officialLinkAvailability = AvailabilityStatus.InStock,
            onOfficialLinkClick = { url -> 
                // Handle link click
            }
        )
    }
}

@Preview
@Composable
fun ProductLinkCardVariationsPreview() {
    PopTrackerTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Out of stock variant
            ProductLinkCard(
                productImage = painterResource(Res.drawable.labubu_demo),
                links = listOf(
                    ProductLink(
                        title = "Sold Out Item",
                        subtitle = "Currently Unavailable",
                        websiteName = "Store",
                        websiteIcon = painterResource(Res.drawable.compose_multiplatform),
                        websiteDomain = WebsiteDomain.Popmart,
                        price = "25.99",
                        availability = AvailabilityStatus.OutOfStock
                    )
                )
            )

            // Coming soon variant
            ProductLinkCard(
                productImage = painterResource(Res.drawable.labubu_demo),
                links = listOf(
                    ProductLink(
                        title = "New Release",
                        subtitle = "Pre-order Available",
                        websiteName = "Official Store",
                        websiteIcon = painterResource(Res.drawable.compose_multiplatform),
                        websiteDomain = WebsiteDomain.Popmart,
                        price = "19.99",
                        availability = AvailabilityStatus.ComingSoon
                    )
                ),
                config = ProductLinkCardConfig(
                    overlayGradient = false
                )
            )
        }
    }
}