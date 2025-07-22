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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
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
import je.ramos.poptracker.everyui.components.EveryImage
import je.ramos.poptracker.everyui.components.EveryImageConfig
import je.ramos.poptracker.everyui.components.ImageSource
import je.ramos.poptracker.everyui.theme.AspectRatioToken
import je.ramos.poptracker.everyui.theme.ExtendedTheme
import je.ramos.poptracker.everyui.theme.PaddingToken
import je.ramos.poptracker.everyui.theme.ShapeToken
import je.ramos.poptracker.everyui.theme.extendedShapes
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import poptracker.composeapp.generated.resources.Res
import poptracker.composeapp.generated.resources.amazon
import poptracker.composeapp.generated.resources.compose_multiplatform
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

enum class WebsiteDomain(val backgroundColor: Color) {
    Amazon(Color.Yellow.copy(alpha = 0.1f)),
    Popmart(Color.Red),
    Tiktok(Color.White),
    Shopee(Color.Red.copy(alpha=0.1f)),
    Lazada(Color.Blue.copy(alpha = 0.3f))
}

enum class AvailabilityStatus(val label: String, val color: Color) {
    InStock("In Stock", Color(0xFF4CAF50)),
    OutOfStock("Out of Stock", Color(0xFFF44336)),
    ComingSoon("Coming Soon", Color(0xFFFF9800)),
    Limited("Limited", Color(0xFFE91E63))
}

@Immutable
data class ProductLinkCardConfig(
    val imageConfig: EveryImageConfig = EveryImageConfig(
        aspectRatio = AspectRatioToken.Square,
        shape = ShapeToken.Large
    ),
    val cardElevation: androidx.compose.ui.unit.Dp = 8.dp,
    val overlayGradient: Boolean = true
)

/**
 * Product card with immersive image and overlay content.
 *
 * Features an immersive square image with title/subtitle overlay,
 * availability badge, and website links with prices and external link indicators.
 *
 * @param productImage Image source for the main product image
 * @param links List of website links with prices for this product
 * @param modifier Modifier for the card
 * @param config Configuration for card appearance and image settings
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ProductLinkCard(
    productImage: ImageSource,
    links: List<ProductLink>,
    modifier: Modifier = Modifier,
    config: ProductLinkCardConfig = ProductLinkCardConfig()
) {
    Card(
        modifier = modifier.width(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = config.cardElevation),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            // Immersive top image with overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                EveryImage(
                    source = productImage,
                    contentDescription = links.firstOrNull()?.title,
                    modifier = Modifier.fillMaxSize(),
                    config = config.imageConfig.copy(
                        aspectRatio = AspectRatioToken.Square,
                        shape = ShapeToken.None,
                        padding = PaddingToken.None
                    )
                )
                links.firstOrNull()?.let { firstLink ->
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.7f)
                                        ),
                                        startY = 0f,
                                        endY = 200f
                                    )
                                ).padding(horizontal = 8.dp, vertical = 8.dp),
                            contentAlignment = Alignment.BottomStart
                        ) {
                            Text(
                                text = firstLink.title,
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

            // Website links section
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                links.forEach { link ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Website icon and name
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier.size(24.dp).clip(MaterialTheme.extendedShapes.small).background(link.websiteDomain.backgroundColor).padding(2.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = link.websiteIcon,
                                    contentDescription = link.websiteName,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }
//                            Box(
//                                modifier = Modifier.size(24.dp).background(
//                                    Color.LightGray.copy(alpha = 0.1f),
//                                    shape = MaterialTheme.extendedShapes.large
//                                ).padding(4.dp)
//                            ) {
//                                EveryImage(
//                                    source = ImageSource.Drawable(link.websiteIcon),
//                                    contentDescription = link.websiteName,
//                                    config = EveryImageConfig(
//                                        aspectRatio = AspectRatioToken.Square,
//                                        shape = ShapeToken.None,
//                                        backgroundColor = Color.Transparent,
//                                        contentScale = ContentScale.Inside
//                                    )
//                                )
//                            }
                            Text(
                                text = link.websiteName,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // Price and external link icon
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "${link.currency}${link.price}",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "↗",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

// Preview Section
@Preview
@Composable
fun ProductLinkCardPreview() {
    ExtendedTheme {
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
            productImage = ImageSource.Drawable(painterResource(Res.drawable.labubu_demo)),
            links = sampleLinks
        )
    }
}

@Preview
@Composable
fun ProductLinkCardVariationsPreview() {
    ExtendedTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Out of stock variant
            ProductLinkCard(
                productImage = ImageSource.Drawable(painterResource(Res.drawable.labubu_demo)),
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
                productImage = ImageSource.Drawable(painterResource(Res.drawable.labubu_demo)),
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