package je.ramos.poptracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import je.ramos.poptracker.ui.components.product.Marketplace
import je.ramos.poptracker.ui.components.product.MarketplaceLink
import je.ramos.poptracker.ui.components.product.MarketplaceType
import je.ramos.poptracker.ui.components.product.Product
import je.ramos.poptracker.ui.components.product.ProductCard
import je.ramos.poptracker.ui.components.product.ProductPrice
import je.ramos.poptracker.ui.theme.PopTrackerTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import poptracker.composeapp.generated.resources.Res
import poptracker.composeapp.generated.resources.labubu_demo
import poptracker.composeapp.generated.resources.lazada
import poptracker.composeapp.generated.resources.popmart
import poptracker.composeapp.generated.resources.shopee
import poptracker.composeapp.generated.resources.tiktok

@Preview
@Composable
fun ProductCardPreview() {
    PopTrackerTheme {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            val popmartMarketplace = Marketplace(
                name = "POPMART",
                icon = painterResource(Res.drawable.popmart),
                type = MarketplaceType.Official
            )
            
            val shopeeMarketplace = Marketplace(
                name = "Shopee",
                icon = painterResource(Res.drawable.shopee),
                type = MarketplaceType.Secondary
            )
            
            val lazadaMarketplace = Marketplace(
                name = "Lazada",
                icon = painterResource(Res.drawable.lazada),
                type = MarketplaceType.Secondary
            )
            
            val tiktokMarketplace = Marketplace(
                name = "TikTok",
                icon = painterResource(Res.drawable.tiktok),
                type = MarketplaceType.Secondary
            )
            
            ProductCard(
                product = Product(
                    id = "1",
                    title = "Have a Seat",
                    subtitle = "THE MONSTER Vinyl Plush Blind box",
                    image = painterResource(Res.drawable.labubu_demo),
                    price = ProductPrice(
                        amount = "900 - 5400",
                        currency = "P"
                    ),
                    marketplaces = listOf(
                        MarketplaceLink(
                            marketplace = popmartMarketplace,
                            url = "https://popmart.com/product/1"
                        ),
                        MarketplaceLink(
                            marketplace = shopeeMarketplace,
                            url = "https://shopee.com/product/1"
                        ),
                        MarketplaceLink(
                            marketplace = lazadaMarketplace,
                            url = "https://lazada.com/product/1"
                        ),
                        MarketplaceLink(
                            marketplace = tiktokMarketplace,
                            url = "https://tiktok.com/product/1"
                        )
                    )
                ),
                onProductClick = { /* Handle product click */ },
                onMarketplaceClick = { /* Handle marketplace click */ }
            )
        }
    }
}

@Preview
@Composable
fun ProductGridPreview() {
    PopTrackerTheme {
        Surface {
            val popmartMarketplace = Marketplace(
                name = "POPMART",
                icon = painterResource(Res.drawable.popmart),
                type = MarketplaceType.Official
            )
            
            val shopeeMarketplace = Marketplace(
                name = "Shopee",
                icon = painterResource(Res.drawable.shopee),
                type = MarketplaceType.Secondary
            )
            
            val lazadaMarketplace = Marketplace(
                name = "Lazada",
                icon = painterResource(Res.drawable.lazada),
                type = MarketplaceType.Secondary
            )
            
            val sampleProduct = Product(
                id = "1",
                title = "The Monster - Have a Seat",
                subtitle = "The Monster Labubu v2",
                image = painterResource(Res.drawable.labubu_demo),
                price = ProductPrice(
                    amount = "900 - 5400",
                    currency = "P"
                ),
                marketplaces = listOf(
                    MarketplaceLink(
                        marketplace = popmartMarketplace,
                        url = "https://popmart.com/product/1"
                    ),
                    MarketplaceLink(
                        marketplace = shopeeMarketplace,
                        url = "https://shopee.com/product/1"
                    ),
                    MarketplaceLink(
                        marketplace = lazadaMarketplace,
                        url = "https://lazada.com/product/1"
                    )
                )
            )
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(4) {
                    ProductCard(
                        product = sampleProduct,
                        onProductClick = { /* Handle product click */ },
                        onMarketplaceClick = { /* Handle marketplace click */ }
                    )
                }
            }
        }
    }
}