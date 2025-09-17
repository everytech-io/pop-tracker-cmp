package io.everytech.poptracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import io.everytech.poptracker.data.ProductRepository
import io.everytech.poptracker.ui.components.product.AvailabilityStatus
import io.everytech.poptracker.ui.components.product.MarketplaceLink
import io.everytech.poptracker.ui.components.product.MarketplaceType
import io.everytech.poptracker.ui.components.product.Product
import io.everytech.poptracker.ui.components.product.ProductPrice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrackerViewModel : ViewModel() {
    private val repository = ProductRepository()
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)

    val products = _products.asStateFlow()
    val isLoading = _isLoading.asStateFlow()
    val error = _error.asStateFlow()

    init {
        loadProducts()
    }
    
    private fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                // Use real-time updates from Firestore
                repository.observeProducts().collect { productList ->
                    Logger.i { "Received ${productList.size} products from Firestore" }
                    
                    if (productList.isEmpty()) {
                        Logger.i { "Products are empty, loading fallback data" }
                        // If Firestore is empty, use fallback data for demo
                        loadFallbackProducts()
                    } else {
                        _products.value = productList
                    }
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                Logger.e(e) { "Failed to load products" }
                _error.value = "Failed to load products: ${e.message}"
                // Load fallback data on error
                loadFallbackProducts()
                _isLoading.value = false
            }
        }
    }
    
    fun refreshProducts() {
        loadProducts()
    }
    
    private fun loadFallbackProducts() {
        Logger.i { "Loading fallback products..." }
        // Fallback demo data in case Firestore is empty or unavailable
        val fallbackProducts = listOf(
            Product(
                id = "demo-1",
                name = "Labubu Halloween Keychain",
                description = "Limited Edition Collectible",
                imageName = "labubu_demo",
                price = ProductPrice(amount = "15.99"),
                officialUrl = "https://www.popmart.com/products/labubu-halloween",
                marketplaces = listOf(
                    MarketplaceLink(
                        name = "Popmart",
                        iconName = "popmart",
                        type = MarketplaceType.Official,
                        url = "https://www.popmart.com",
                        availability = AvailabilityStatus.InStock
                    ),
                    MarketplaceLink(
                        name = "Shopee",
                        iconName = "shopee",
                        type = MarketplaceType.Secondary,
                        url = "https://shopee.com",
                        availability = AvailabilityStatus.OutOfStock
                    ),
                    MarketplaceLink(
                        name = "Lazada",
                        iconName = "lazada",
                        type = MarketplaceType.Secondary,
                        url = "https://lazada.com",
                        availability = AvailabilityStatus.InStock
                    ),
                    MarketplaceLink(
                        name = "TikTok",
                        iconName = "tiktok",
                        type = MarketplaceType.Secondary,
                        url = "https://tiktok.com",
                        availability = AvailabilityStatus.InStock
                    ),
                )
            ),
            Product(
                id = "demo-2",
                name = "Crybaby x Labubu Blind Box",
                description = "Series 1 Mystery Figure",
                imageName = "labubu_demo",
                price = ProductPrice(amount = "12.99"),
                officialUrl = "https://www.popmart.com/products/crybaby-labubu",
                marketplaces = emptyList()
            ),
            Product(
                id = "demo-3",
                name = "Dimoo Space Travel Series",
                description = "Astronaut Edition",
                imageName = "labubu_demo",
                price = ProductPrice(amount = "14.99"),
                officialUrl = "https://www.popmart.com/products/dimoo-space-travel",
                marketplaces = emptyList()
            ),
            Product(
                id = "demo-4",
                name = "Skull Panda City of Night",
                description = "Glow in the Dark",
                imageName = "labubu_demo",
                price = ProductPrice(amount = "16.99"),
                officialUrl = "https://www.popmart.com/products/skull-panda-city-night",
                marketplaces = emptyList()
            )
        )
        
        _products.value = fallbackProducts
        Logger.i { "Loaded ${fallbackProducts.size} fallback products" }
    }
}
