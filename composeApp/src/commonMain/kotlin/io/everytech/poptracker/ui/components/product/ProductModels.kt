package io.everytech.poptracker.ui.components.product

import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import poptracker.composeapp.generated.resources.*

@Serializable
data class Product(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageName: String,
    val price: ProductPrice,
    val marketplaces: List<MarketplaceLink> = emptyList()
)

@Serializable
data class ProductPrice(
    val amount: String,
    val currency: String = "$",
    val range: String? = null // e.g., "900 - 5400"
)

@Serializable
data class MarketplaceLink(
    val name: String,
    val iconName: String? = null,
    val type: MarketplaceType,
    val url: String,
    val price: ProductPrice? = null,
    val availability: AvailabilityStatus = AvailabilityStatus.InStock
)

@Serializable
enum class MarketplaceType {
    Official,    // PopMart official
    Primary,     // Main marketplaces like Amazon
    Secondary    // Secondary marketplaces like Shopee, Lazada
}

@Serializable
enum class AvailabilityStatus {
    InStock,
    OutOfStock,
    ComingSoon,
    Limited,
    PreOrder
}

// Helper object to resolve drawable resources from string names
object DrawableResolver {
    // Default fallbacks
    val defaultProductImage: DrawableResource = Res.drawable.icon_collection
    val defaultMarketplaceIcon: DrawableResource = Res.drawable.icon_open_link
    
    fun resolveDrawable(name: String): DrawableResource? {
        return when (name) {
            // Product images
            "labubu_demo" -> Res.drawable.labubu_demo
            "icon_pop_now" -> Res.drawable.icon_pop_now
            
            // Marketplace icons
            "popmart" -> Res.drawable.popmart
            "shopee" -> Res.drawable.shopee
            "lazada" -> Res.drawable.lazada
            "tiktok" -> Res.drawable.tiktok
            "amazon" -> Res.drawable.amazon
            
            // General icons
            "compose_multiplatform" -> Res.drawable.compose_multiplatform
            "icon_open_link" -> Res.drawable.icon_open_link
            "icon_collection" -> Res.drawable.icon_collection
            
            else -> null
        }
    }
}

// Extension properties for easy access to resolved resources with defaults
val Product.imageResource: DrawableResource
    get() = DrawableResolver.resolveDrawable(imageName) ?: DrawableResolver.defaultProductImage

val MarketplaceLink.iconResource: DrawableResource
    get() = iconName?.let { DrawableResolver.resolveDrawable(it) } ?: DrawableResolver.defaultMarketplaceIcon