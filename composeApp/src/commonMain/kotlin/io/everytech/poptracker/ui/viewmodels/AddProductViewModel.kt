package io.everytech.poptracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import io.everytech.poptracker.data.ProductRepository
import io.everytech.poptracker.ui.components.product.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class AddProductUiState(
    val name: String = "",
    val description: String = "",
    val imageName: String = "labubu_demo",
    val price: ProductPrice = ProductPrice("", "SGD"),
    val officialUrl: String = "",
    val marketplaces: List<MarketplaceLink> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val isValid: Boolean
        get() = name.isNotBlank() && 
                description.isNotBlank() && 
                price.amount.isNotBlank() &&
                imageName.isNotBlank() &&
                officialUrl.isNotBlank()
}

class AddProductViewModel(
    private val countryCode: String = "sg"
) : ViewModel() {
    
    private val repository = ProductRepository(countryCode)
    
    private val _uiState = MutableStateFlow(
        AddProductUiState(
            price = ProductPrice(
                amount = "",
                currency = getCurrencyForCountry(countryCode)
            )
        )
    )
    val uiState: StateFlow<AddProductUiState> = _uiState.asStateFlow()
    
    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name, error = null)
    }
    
    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description, error = null)
    }
    
    fun updateImageName(imageName: String) {
        _uiState.value = _uiState.value.copy(imageName = imageName, error = null)
    }
    
    fun updatePrice(price: ProductPrice) {
        _uiState.value = _uiState.value.copy(price = price, error = null)
    }
    
    fun updateOfficialUrl(url: String) {
        _uiState.value = _uiState.value.copy(
            officialUrl = url,
            error = null
        )
    }
    
    fun addMarketplace() {
        val newMarketplace = MarketplaceLink(
            name = "",
            iconName = getDefaultIconForCountry(countryCode),
            type = MarketplaceType.Secondary,
            url = "",
            availability = AvailabilityStatus.InStock
        )
        val updatedMarketplaces = _uiState.value.marketplaces + newMarketplace
        _uiState.value = _uiState.value.copy(marketplaces = updatedMarketplaces, error = null)
    }
    
    fun updateMarketplace(index: Int, marketplace: MarketplaceLink) {
        val updatedMarketplaces = _uiState.value.marketplaces.toMutableList()
        if (index in updatedMarketplaces.indices) {
            updatedMarketplaces[index] = marketplace
            _uiState.value = _uiState.value.copy(marketplaces = updatedMarketplaces, error = null)
        }
    }
    
    fun removeMarketplace(index: Int) {
        val updatedMarketplaces = _uiState.value.marketplaces.toMutableList()
        if (index in updatedMarketplaces.indices) {
            updatedMarketplaces.removeAt(index)
            _uiState.value = _uiState.value.copy(marketplaces = updatedMarketplaces, error = null)
        }
    }
    
    @OptIn(ExperimentalUuidApi::class)
    fun saveProduct(onSuccess: () -> Unit) {
        if (!_uiState.value.isValid) {
            _uiState.value = _uiState.value.copy(error = "Please fill in all required fields")
            return
        }
        
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                
                val state = _uiState.value
                val product = Product(
                    id = Uuid.random().toString(),
                    name = state.name,
                    description = state.description,
                    imageName = state.imageName,
                    price = state.price,
                    officialUrl = state.officialUrl,
                    marketplaces = state.marketplaces,
                    createdAt = System.currentTimeMillis().toString()
                )
                
                repository.addProduct(product)
                Logger.i { "Product saved successfully: ${product.id}" }
                
                _uiState.value = _uiState.value.copy(isLoading = false)
                onSuccess()
                
            } catch (e: Exception) {
                Logger.e(e) { "Failed to save product" }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to save product: ${e.message}"
                )
            }
        }
    }
    
    private fun getCurrencyForCountry(countryCode: String): String {
        return when (countryCode.lowercase()) {
            "us" -> "USD"
            "ph" -> "PHP"
            "my" -> "MYR"
            "sg" -> "SGD"
            "global" -> "USD"
            else -> "USD"
        }
    }
    
    private fun getDefaultIconForCountry(countryCode: String): String {
        return when (countryCode.lowercase()) {
            "sg", "my" -> "shopee"
            "us" -> "amazon"
            "ph" -> "lazada"
            else -> "shopee"
        }
    }
}