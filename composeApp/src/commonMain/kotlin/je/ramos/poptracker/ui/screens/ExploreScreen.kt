package je.ramos.poptracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.painter.Painter
import je.ramos.poptracker.ui.theme.components.AvailabilityStatus
import je.ramos.poptracker.ui.theme.components.ProductLink
import je.ramos.poptracker.ui.theme.components.ProductLinkCard
import je.ramos.poptracker.ui.theme.components.WebsiteDomain
import org.jetbrains.compose.resources.painterResource
import poptracker.composeapp.generated.resources.Res
import poptracker.composeapp.generated.resources.amazon
import poptracker.composeapp.generated.resources.labubu_demo
import poptracker.composeapp.generated.resources.lazada
import poptracker.composeapp.generated.resources.popmart
import poptracker.composeapp.generated.resources.shopee
import poptracker.composeapp.generated.resources.tiktok

data class ProductItem(
    val id: String,
    val productImage: Painter,
    val links: List<ProductLink>
)

@Composable
fun ExploreScreen(modifier: Modifier = Modifier) {
    val mockProducts = createMockProducts()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Explore",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Discover new products",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(mockProducts) { product ->
                ProductLinkCard(
                    productImage = product.productImage,
                    links = product.links
                )
            }
        }
    }
}

@Composable
private fun createMockProducts(): List<ProductItem> {
    return listOf(
        ProductItem(
            id = "1",
            productImage = painterResource(Res.drawable.labubu_demo),
            links = listOf(
                ProductLink(
                    title = "Labubu Halloween Keychain",
                    subtitle = "Limited Edition Collectible",
                    websiteName = "PopMart",
                    websiteIcon = painterResource(Res.drawable.popmart),
                    websiteDomain = WebsiteDomain.Popmart,
                    price = "15.99"
                ),
                ProductLink(
                    title = "Labubu Halloween Keychain",
                    subtitle = "Limited Edition Collectible",
                    websiteName = "Amazon",
                    websiteIcon = painterResource(Res.drawable.amazon),
                    websiteDomain = WebsiteDomain.Amazon,
                    price = "18.50"
                ),
                ProductLink(
                    title = "Labubu Halloween Keychain",
                    subtitle = "Limited Edition Collectible",
                    websiteName = "TikTok",
                    websiteIcon = painterResource(Res.drawable.tiktok),
                    websiteDomain = WebsiteDomain.Tiktok,
                    price = "22.00"
                )
            )
        ),
        ProductItem(
            id = "2",
            productImage = painterResource(Res.drawable.labubu_demo),
            links = listOf(
                ProductLink(
                    title = "Labubu Christmas Special",
                    subtitle = "Holiday Edition",
                    websiteName = "Shopee",
                    websiteIcon = painterResource(Res.drawable.shopee),
                    websiteDomain = WebsiteDomain.Shopee,
                    price = "24.99",
                    availability = AvailabilityStatus.InStock
                ),
                ProductLink(
                    title = "Labubu Christmas Special",
                    subtitle = "Holiday Edition",
                    websiteName = "Lazada",
                    websiteIcon = painterResource(Res.drawable.lazada),
                    websiteDomain = WebsiteDomain.Lazada,
                    price = "26.50",
                    availability = AvailabilityStatus.Limited
                )
            )
        ),
        ProductItem(
            id = "3",
            productImage = painterResource(Res.drawable.labubu_demo),
            links = listOf(
                ProductLink(
                    title = "Labubu Valentine Edition",
                    subtitle = "Love Series",
                    websiteName = "PopMart",
                    websiteIcon = painterResource(Res.drawable.popmart),
                    websiteDomain = WebsiteDomain.Popmart,
                    price = "19.99"
                ),
                ProductLink(
                    title = "Labubu Valentine Edition",
                    subtitle = "Love Series",
                    websiteName = "TikTok",
                    websiteIcon = painterResource(Res.drawable.tiktok),
                    websiteDomain = WebsiteDomain.Tiktok,
                    price = "21.99",
                    availability = AvailabilityStatus.OutOfStock
                )
            )
        ),
        ProductItem(
            id = "4",
            productImage = painterResource(Res.drawable.labubu_demo),
            links = listOf(
                ProductLink(
                    title = "Labubu Space Explorer",
                    subtitle = "Galaxy Series",
                    websiteName = "Amazon",
                    websiteIcon = painterResource(Res.drawable.amazon),
                    websiteDomain = WebsiteDomain.Amazon,
                    price = "29.99"
                ),
                ProductLink(
                    title = "Labubu Space Explorer",
                    subtitle = "Galaxy Series",
                    websiteName = "Shopee",
                    websiteIcon = painterResource(Res.drawable.shopee),
                    websiteDomain = WebsiteDomain.Shopee,
                    price = "27.50"
                ),
                ProductLink(
                    title = "Labubu Space Explorer",
                    subtitle = "Galaxy Series",
                    websiteName = "Lazada",
                    websiteIcon = painterResource(Res.drawable.lazada),
                    websiteDomain = WebsiteDomain.Lazada,
                    price = "28.99"
                )
            )
        ),
        ProductItem(
            id = "5",
            productImage = painterResource(Res.drawable.labubu_demo),
            links = listOf(
                ProductLink(
                    title = "Labubu Summer Beach",
                    subtitle = "Vacation Series",
                    websiteName = "PopMart",
                    websiteIcon = painterResource(Res.drawable.popmart),
                    websiteDomain = WebsiteDomain.Popmart,
                    price = "17.99"
                ),
                ProductLink(
                    title = "Labubu Summer Beach",
                    subtitle = "Vacation Series",
                    websiteName = "TikTok",
                    websiteIcon = painterResource(Res.drawable.tiktok),
                    websiteDomain = WebsiteDomain.Tiktok,
                    price = "19.50",
                    availability = AvailabilityStatus.ComingSoon
                )
            )
        ),
        ProductItem(
            id = "6",
            productImage = painterResource(Res.drawable.labubu_demo),
            links = listOf(
                ProductLink(
                    title = "Labubu Zodiac Collection",
                    subtitle = "Astrology Series",
                    websiteName = "Amazon",
                    websiteIcon = painterResource(Res.drawable.amazon),
                    websiteDomain = WebsiteDomain.Amazon,
                    price = "25.99"
                ),
                ProductLink(
                    title = "Labubu Zodiac Collection",
                    subtitle = "Astrology Series",
                    websiteName = "Shopee",
                    websiteIcon = painterResource(Res.drawable.shopee),
                    websiteDomain = WebsiteDomain.Shopee,
                    price = "23.50"
                ),
                ProductLink(
                    title = "Labubu Zodiac Collection",
                    subtitle = "Astrology Series",
                    websiteName = "Lazada",
                    websiteIcon = painterResource(Res.drawable.lazada),
                    websiteDomain = WebsiteDomain.Lazada,
                    price = "24.99"
                ),
                ProductLink(
                    title = "Labubu Zodiac Collection",
                    subtitle = "Astrology Series",
                    websiteName = "PopMart",
                    websiteIcon = painterResource(Res.drawable.popmart),
                    websiteDomain = WebsiteDomain.Popmart,
                    price = "22.99"
                )
            )
        )
    )
}