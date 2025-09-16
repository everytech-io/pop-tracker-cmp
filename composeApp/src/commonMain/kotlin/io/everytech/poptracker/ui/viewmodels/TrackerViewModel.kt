package io.everytech.poptracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import io.everytech.poptracker.ui.components.product.AvailabilityStatus
import io.everytech.poptracker.ui.components.product.MarketplaceLink
import io.everytech.poptracker.ui.components.product.MarketplaceType
import io.everytech.poptracker.ui.components.product.Product
import io.everytech.poptracker.ui.components.product.ProductPrice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrackerViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())

    val products = _products.asStateFlow()

    init {

        _products.value = listOf(
            Product(
                id = "1",
                title = "Labubu Halloween Keychain",
                subtitle = "Limited Edition Collectible",
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
                        "Popmart",
                        iconName = "lazada",
                        type = MarketplaceType.Secondary,
                        url = "",
                        availability = AvailabilityStatus.InStock
                    ),
                    MarketplaceLink(
                        "Popmart",
                        iconName = "tiktok",
                        type = MarketplaceType.Secondary,
                        url = "",
                        availability = AvailabilityStatus.InStock
                    ),
                )
            ),
            Product(
                id = "2",
                title = "Crybaby x Labubu Blind Box",
                subtitle = "Series 1 Mystery Figure",
                imageName = "labubu_demo",
                price = ProductPrice(amount = "12.99")
            ),
            Product(
                id = "3",
                title = "Dimoo Space Travel Series",
                subtitle = "Astronaut Edition",
                imageName = "labubu_demo",
                price = ProductPrice(amount = "14.99")
            ),
            Product(
                id = "4",
                title = "Skull Panda City of Night",
                subtitle = "Glow in the Dark",
                imageName = "labubu_demo",
                price = ProductPrice(amount = "16.99")
            )
        )

    }

    // ...
}
