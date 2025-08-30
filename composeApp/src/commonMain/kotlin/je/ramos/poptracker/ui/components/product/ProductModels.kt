package je.ramos.poptracker.ui.components.product

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.DrawableResource

@Immutable
data class Product(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageDrawableResource: DrawableResource,
    val price: ProductPrice,
    val marketplaces: List<MarketplaceLink> = emptyList()
)

@Immutable
data class ProductPrice(
    val amount: String,
    val currency: String = "$",
    val range: String? = null // e.g., "900 - 5400"
)

@Immutable
data class MarketplaceLink(
    val name: String,
    val iconDrawableResource: DrawableResource,
    val type: MarketplaceType,
    val url: String,
    val price: ProductPrice? = null,
    val availability: AvailabilityStatus = AvailabilityStatus.InStock
)

enum class MarketplaceType {
    Official,    // PopMart official
    Primary,     // Main marketplaces like Amazon
    Secondary    // Secondary marketplaces like Shopee, Lazada
}

enum class AvailabilityStatus {
    InStock,
    OutOfStock,
    ComingSoon,
    Limited,
    PreOrder
}