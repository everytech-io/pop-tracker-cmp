package je.ramos.poptracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import je.ramos.poptracker.ui.components.product.AvailabilityStatus
import je.ramos.poptracker.ui.components.product.MarketplaceLink
import je.ramos.poptracker.ui.components.product.MarketplaceType
import je.ramos.poptracker.ui.components.product.Product
import je.ramos.poptracker.ui.components.product.ProductCard
import je.ramos.poptracker.ui.components.product.ProductPrice
import je.ramos.poptracker.ui.theme.PopTrackerTheme
import je.ramos.poptracker.ui.viewmodels.TrackerViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import poptracker.composeapp.generated.resources.Res
import poptracker.composeapp.generated.resources.labubu_demo
import poptracker.composeapp.generated.resources.lazada
import poptracker.composeapp.generated.resources.popmart
import poptracker.composeapp.generated.resources.shopee
import poptracker.composeapp.generated.resources.tiktok

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackerScreen(
    modifier: Modifier = Modifier,
    trackerViewModel: TrackerViewModel = viewModel {
        TrackerViewModel()
    }
) {

    val products = trackerViewModel.products.value
    TrackerScreenContent(modifier = modifier, products = products)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TrackerScreenContent(
    modifier: Modifier = Modifier,
    products: List<Product>,
) {

    val topAppBarState: TopAppBarState = rememberTopAppBarState()
    val scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)
    
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "Drops",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = paddingValues
        ) {
            items(items = products) {
                ProductCard(product = it)
            }
        }
    }

}

@Preview
@Composable
fun TrackerScreenContentPreview() {
    PopTrackerTheme {
//        val sampleProducts = listOf(
//            Product(
//                id = "1",
//                title = "Labubu Halloween Keychain",
//                subtitle = "Limited Edition Collectible",
//                image = painterResource(Res.drawable.labubu_demo),
//                price = ProductPrice(amount = "15.99"),
//                marketplaces = listOf(
//                    MarketplaceLink(
//                        "Popmart",
//                        icon = painterResource(Res.drawable.popmart),
//                        type = MarketplaceType.Official,
//                        url = "",
//                        availability = AvailabilityStatus.InStock
//                    ),
//                    MarketplaceLink(
//                        "Shopee",
//                        icon = painterResource(Res.drawable.shopee),
//                        type = MarketplaceType.Secondary,
//                        url = "",
//                        availability = AvailabilityStatus.OutOfStock
//                    ),
//                    MarketplaceLink(
//                        "Popmart",
//                        icon = painterResource(Res.drawable.lazada),
//                        type = MarketplaceType.Secondary,
//                        url = "",
//                        availability = AvailabilityStatus.InStock
//                    ),
//                    MarketplaceLink(
//                        "Popmart",
//                        icon = painterResource(Res.drawable.tiktok),
//                        type = MarketplaceType.Secondary,
//                        url = "",
//                        availability = AvailabilityStatus.InStock
//                    ),
//                )
//            ),
//            Product(
//                id = "2",
//                title = "Crybaby x Labubu Blind Box",
//                subtitle = "Series 1 Mystery Figure",
//                image = painterResource(Res.drawable.labubu_demo),
//                price = ProductPrice(amount = "12.99")
//            ),
//            Product(
//                id = "3",
//                title = "Dimoo Space Travel Series",
//                subtitle = "Astronaut Edition",
//                image = painterResource(Res.drawable.labubu_demo),
//                price = ProductPrice(amount = "14.99")
//            ),
//            Product(
//                id = "4",
//                title = "Skull Panda City of Night",
//                subtitle = "Glow in the Dark",
//                image = painterResource(Res.drawable.labubu_demo),
//                price = ProductPrice(amount = "16.99")
//            )
//        )

        val sampleProducts = emptyList<Product>()
        TrackerScreenContent(
            modifier = Modifier,
            products = sampleProducts
        )
    }
}