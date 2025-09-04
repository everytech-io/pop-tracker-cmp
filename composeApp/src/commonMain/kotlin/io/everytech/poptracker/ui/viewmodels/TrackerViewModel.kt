package io.everytech.poptracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import io.everytech.poptracker.ui.components.product.AvailabilityStatus
import io.everytech.poptracker.ui.components.product.MarketplaceLink
import io.everytech.poptracker.ui.components.product.MarketplaceType
import io.everytech.poptracker.ui.components.product.Product
import io.everytech.poptracker.ui.components.product.ProductPrice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import poptracker.composeapp.generated.resources.Res
import poptracker.composeapp.generated.resources.labubu_demo
import poptracker.composeapp.generated.resources.lazada
import poptracker.composeapp.generated.resources.popmart
import poptracker.composeapp.generated.resources.shopee
import poptracker.composeapp.generated.resources.tiktok

class TrackerViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())

    val products = _products.asStateFlow()

    init {

        _products.value = listOf(
            Product(
                id = "1",
                title = "Labubu Halloween Keychain",
                subtitle = "Limited Edition Collectible",
                imageDrawableResource = Res.drawable.labubu_demo,
                price = ProductPrice(amount = "15.99"),
                marketplaces = listOf(
                    MarketplaceLink(
                        "Popmart",
                        iconDrawableResource = Res.drawable.popmart,
                        type = MarketplaceType.Official,
                        url = "",
                        availability = AvailabilityStatus.InStock
                    ),
                    MarketplaceLink(
                        "Shopee",
                        iconDrawableResource = Res.drawable.shopee,
                        type = MarketplaceType.Secondary,
                        url = "",
                        availability = AvailabilityStatus.OutOfStock
                    ),
                    MarketplaceLink(
                        "Popmart",
                        iconDrawableResource = Res.drawable.lazada,
                        type = MarketplaceType.Secondary,
                        url = "",
                        availability = AvailabilityStatus.InStock
                    ),
                    MarketplaceLink(
                        "Popmart",
                        iconDrawableResource = Res.drawable.tiktok,
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
                imageDrawableResource = Res.drawable.labubu_demo,
                price = ProductPrice(amount = "12.99")
            ),
            Product(
                id = "3",
                title = "Dimoo Space Travel Series",
                subtitle = "Astronaut Edition",
                imageDrawableResource = Res.drawable.labubu_demo,
                price = ProductPrice(amount = "14.99")
            ),
            Product(
                id = "4",
                title = "Skull Panda City of Night",
                subtitle = "Glow in the Dark",
                imageDrawableResource = Res.drawable.labubu_demo,
                price = ProductPrice(amount = "16.99")
            )
        )

    }

    // ...
}
