package io.everytech.poptracker.data

data class MarketplaceInfo(
    val id: String,
    val displayName: String,
    val iconName: String,
    val availableInCountries: List<String>,
    val baseUrl: String? = null
)

object MarketplaceConstants {
    val PREDEFINED_MARKETPLACES = listOf(
        MarketplaceInfo(
            id = "shopee",
            displayName = "Shopee",
            iconName = "shopee",
            availableInCountries = listOf("sg", "my", "ph"),
            baseUrl = "https://shopee."
        ),
        MarketplaceInfo(
            id = "lazada",
            displayName = "Lazada",
            iconName = "lazada",
            availableInCountries = listOf("sg", "my", "ph"),
            baseUrl = "https://www.lazada."
        ),
        MarketplaceInfo(
            id = "amazon",
            displayName = "Amazon",
            iconName = "amazon",
            availableInCountries = listOf("us", "global"),
            baseUrl = "https://www.amazon.com"
        ),
        MarketplaceInfo(
            id = "tiktok",
            displayName = "TikTok Shop",
            iconName = "tiktok",
            availableInCountries = listOf("us", "sg", "my", "ph"),
            baseUrl = "https://shop.tiktok.com"
        )
    )
    
    fun getMarketplacesForCountry(countryCode: String): List<MarketplaceInfo> {
        return PREDEFINED_MARKETPLACES.filter { 
            it.availableInCountries.contains(countryCode.lowercase())
        }
    }
    
    fun getCountrySpecificUrl(marketplace: MarketplaceInfo, countryCode: String): String {
        return when (marketplace.id) {
            "shopee" -> when (countryCode.lowercase()) {
                "sg" -> "https://shopee.sg"
                "my" -> "https://shopee.com.my"
                "ph" -> "https://shopee.ph"
                else -> marketplace.baseUrl ?: ""
            }
            "lazada" -> when (countryCode.lowercase()) {
                "sg" -> "https://www.lazada.sg"
                "my" -> "https://www.lazada.com.my"
                "ph" -> "https://www.lazada.com.ph"
                else -> marketplace.baseUrl ?: ""
            }
            else -> marketplace.baseUrl ?: ""
        }
    }
}

data class CountryInfo(
    val code: String,
    val displayName: String,
    val flag: String,
    val currency: String
)

object CountryConstants {
    val SUPPORTED_COUNTRIES = listOf(
        CountryInfo("us", "United States", "🇺🇸", "USD"),
        CountryInfo("ph", "Philippines", "🇵🇭", "PHP"),
        CountryInfo("my", "Malaysia", "🇲🇾", "MYR"),
        CountryInfo("sg", "Singapore", "🇸🇬", "SGD")
    )
    
    fun getCountryInfo(code: String): CountryInfo {
        return SUPPORTED_COUNTRIES.find { it.code == code.lowercase() }
            ?: CountryInfo("global", "Global", "🌍", "USD")
    }
}