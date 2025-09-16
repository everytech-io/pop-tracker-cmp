package io.everytech.poptracker.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.everytech.poptracker.ui.components.everyapp.*
import io.everytech.poptracker.ui.components.product.*
import io.everytech.poptracker.ui.theme.PopTrackerTheme
import io.everytech.poptracker.ui.viewmodels.AddProductViewModel
import io.everytech.poptracker.data.CountryConstants
import io.everytech.poptracker.data.MarketplaceConstants
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AddProductScreen(
    modifier: Modifier = Modifier,
    initialCountryCode: String = "sg",
    onBackClick: () -> Unit = {},
    onProductSaved: () -> Unit = {},
) {
    var selectedCountry by remember { mutableStateOf(initialCountryCode) }
    val viewModel: AddProductViewModel = viewModel(key = selectedCountry) { 
        AddProductViewModel(selectedCountry) 
    }
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add Product",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            viewModel.saveProduct { onProductSaved() }
                        },
                        enabled = uiState.isValid && !uiState.isLoading,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            Icons.Filled.Save,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Save", fontWeight = FontWeight.Medium)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.saveProduct { onProductSaved() }
                },
                containerColor = if (uiState.isValid && !uiState.isLoading) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
                contentColor = if (uiState.isValid && !uiState.isLoading) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Icon(Icons.Filled.Save, "Save Product")
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
    ) { paddingValues ->
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            
            // Country Selector
            CountrySelector(
                selectedCountry = selectedCountry,
                onCountryChange = { newCountry ->
                    selectedCountry = newCountry
                }
            )
            
            // Product Image Section
            ProductImageSection(
                selectedImage = uiState.imageName,
                onImageSelected = viewModel::updateImageName
            )
            
            // Basic Info Section
            BasicInfoSection(
                name = uiState.name,
                onNameChange = viewModel::updateName,
                description = uiState.description,
                onDescriptionChange = viewModel::updateDescription
            )
            
            // Pricing Section
            PricingSection(
                amount = uiState.price.amount,
                onAmountChange = { viewModel.updatePrice(uiState.price.copy(amount = it)) },
                currency = uiState.price.currency
            )
            
            // Official Store Section
            OfficialStoreSection(
                officialUrl = uiState.officialUrl,
                onOfficialUrlChange = viewModel::updateOfficialUrl
            )
            
            // Marketplaces Section
            MarketplacesSection(
                marketplaces = uiState.marketplaces,
                onAddMarketplace = viewModel::addMarketplace,
                onUpdateMarketplace = viewModel::updateMarketplace,
                onRemoveMarketplace = viewModel::removeMarketplace,
                countryCode = selectedCountry
            )
            
            // Loading state
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            // Error state
            uiState.error?.let { error ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = error,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
            
            // Bottom spacing for FAB
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun CountrySelector(
    selectedCountry: String,
    onCountryChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val countries = CountryConstants.SUPPORTED_COUNTRIES
    val selectedCountryInfo = CountryConstants.getCountryInfo(selectedCountry)
    
    EveryCard(
        modifier = Modifier.fillMaxWidth(),
        config = EveryCardConfig(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            EveryText(
                text = "📍 Target Market",
                config = EveryTextConfig(
                    style = EveryTextStyle.TitleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            )
            
            Box {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    value = "${selectedCountryInfo.flag} ${selectedCountryInfo.displayName}",
                    onValueChange = {},
                    label = { Text("Country") },
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(Icons.Default.ArrowDropDown, "Select Country")
                        }
                    }
                )
                
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    countries.forEach { country ->
                        DropdownMenuItem(
                            text = {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(country.flag)
                                    Text(country.displayName)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        country.currency,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            },
                            onClick = {
                                onCountryChange(country.code)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun ProductImageSection(
    selectedImage: String,
    onImageSelected: (String) -> Unit
) {
    val availableImages = listOf(
        "labubu_demo" to "Labubu Demo",
        "icon_pop_now" to "Pop Now Icon",
        "icon_collection" to "Collection Icon"
    )
    
    EveryCard(
        modifier = Modifier.fillMaxWidth(),
        config = EveryCardConfig(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            EveryText(
                text = "📸 Product Image",
                config = EveryTextConfig(
                    style = EveryTextStyle.TitleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            )
            
            var expanded by remember { mutableStateOf(false) }
            
            Box {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    readOnly = true,
                    value = availableImages.find { it.first == selectedImage }?.second ?: "Select Image",
                    onValueChange = {},
                    label = { Text("Image") },
                    trailingIcon = { 
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(Icons.Default.ArrowDropDown, "Dropdown")
                        }
                    }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    availableImages.forEach { (value, label) ->
                        DropdownMenuItem(
                            text = { Text(label) },
                            onClick = {
                                onImageSelected(value)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BasicInfoSection(
    name: String,
    onNameChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit
) {
    EveryCard(
        modifier = Modifier.fillMaxWidth(),
        config = EveryCardConfig(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            EveryText(
                text = "📝 Product Details",
                config = EveryTextConfig(
                    style = EveryTextStyle.TitleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            )
            
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Product Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChange,
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )
        }
    }
}

@Composable
private fun PricingSection(
    amount: String,
    onAmountChange: (String) -> Unit,
    currency: String
) {
    EveryCard(
        modifier = Modifier.fillMaxWidth(),
        config = EveryCardConfig(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            EveryText(
                text = "💰 Pricing ($currency)",
                config = EveryTextConfig(
                    style = EveryTextStyle.TitleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            )
            
            OutlinedTextField(
                value = amount,
                onValueChange = onAmountChange,
                label = { Text("Price") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Text(currency, style = MaterialTheme.typography.bodyLarge) },
                singleLine = true
            )
        }
    }
}

@Composable
private fun OfficialStoreSection(
    officialUrl: String,
    onOfficialUrlChange: (String) -> Unit
) {
    EveryCard(
        modifier = Modifier.fillMaxWidth(),
        config = EveryCardConfig(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            EveryText(
                text = "🏪 Official Store",
                config = EveryTextConfig(
                    style = EveryTextStyle.TitleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            )
            
            OutlinedTextField(
                value = officialUrl,
                onValueChange = onOfficialUrlChange,
                label = { Text("Official Store URL") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("https://www.popmart.com/product/...") },
                singleLine = true,
                leadingIcon = {
                    Text("🔗", style = MaterialTheme.typography.bodyLarge)
                }
            )
            
            Text(
                text = "Add the official store link where this product is originally sold",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun MarketplacesSection(
    marketplaces: List<MarketplaceLink>,
    onAddMarketplace: () -> Unit,
    onUpdateMarketplace: (Int, MarketplaceLink) -> Unit,
    onRemoveMarketplace: (Int) -> Unit,
    countryCode: String
) {
    val availableMarketplaces = remember(countryCode) {
        MarketplaceConstants.getMarketplacesForCountry(countryCode)
    }
    
    EveryCard(
        modifier = Modifier.fillMaxWidth(),
        config = EveryCardConfig(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            EveryText(
                text = "🛒 Available Marketplaces",
                config = EveryTextConfig(
                    style = EveryTextStyle.TitleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            )
            
            Text(
                text = "Select where this product is available:",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            // Predefined marketplaces as checkboxes
            availableMarketplaces.forEach { marketplaceInfo ->
                val existingMarketplace = marketplaces.find { 
                    it.name == marketplaceInfo.displayName 
                }
                val isSelected = existingMarketplace != null
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {
                            if (isSelected) {
                                val index = marketplaces.indexOfFirst { 
                                    it.name == marketplaceInfo.displayName 
                                }
                                if (index >= 0) onRemoveMarketplace(index)
                            } else {
                                onAddMarketplace()
                                val newIndex = marketplaces.size
                                val newMarketplace = MarketplaceLink(
                                    name = marketplaceInfo.displayName,
                                    iconName = marketplaceInfo.iconName,
                                    type = MarketplaceType.Secondary,
                                    url = MarketplaceConstants.getCountrySpecificUrl(
                                        marketplaceInfo, countryCode
                                    ),
                                    availability = AvailabilityStatus.InStock
                                )
                                onUpdateMarketplace(newIndex, newMarketplace)
                            }
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) 
                            MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = null // Handled by card click
                        )
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = marketplaceInfo.displayName,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                            
                            if (isSelected && existingMarketplace != null) {
                                // Show URL input for selected marketplace
                                OutlinedTextField(
                                    value = existingMarketplace.url,
                                    onValueChange = { newUrl ->
                                        val index = marketplaces.indexOf(existingMarketplace)
                                        onUpdateMarketplace(
                                            index,
                                            existingMarketplace.copy(url = newUrl)
                                        )
                                    },
                                    label = { Text("Product URL (optional)") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    singleLine = true,
                                    placeholder = { 
                                        Text(MarketplaceConstants.getCountrySpecificUrl(
                                            marketplaceInfo, countryCode
                                        ))
                                    }
                                )
                                
                                // Availability dropdown
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    var availabilityExpanded by remember { mutableStateOf(false) }
                                    
                                    Box(modifier = Modifier.weight(1f)) {
                                        OutlinedTextField(
                                            readOnly = true,
                                            value = existingMarketplace.availability.name,
                                            onValueChange = {},
                                            label = { Text("Availability") },
                                            trailingIcon = {
                                                IconButton(onClick = { 
                                                    availabilityExpanded = !availabilityExpanded 
                                                }) {
                                                    Icon(Icons.Default.ArrowDropDown, "Dropdown")
                                                }
                                            },
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        
                                        DropdownMenu(
                                            expanded = availabilityExpanded,
                                            onDismissRequest = { availabilityExpanded = false }
                                        ) {
                                            AvailabilityStatus.entries.forEach { status ->
                                                DropdownMenuItem(
                                                    text = { Text(status.name) },
                                                    onClick = {
                                                        val index = marketplaces.indexOf(existingMarketplace)
                                                        onUpdateMarketplace(
                                                            index,
                                                            existingMarketplace.copy(availability = status)
                                                        )
                                                        availabilityExpanded = false
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            if (availableMarketplaces.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No marketplaces available for this country",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AddProductScreenPreview() {
    PopTrackerTheme {
        // Create a simple preview without ViewModel dependency
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Add Product",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {}
                ) {
                    Icon(Icons.Filled.Save, "Save Product")
                }
            },
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Country Selector Preview
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "📍 Target Market",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                            value = "🇸🇬 Singapore",
                            onValueChange = {},
                            label = { Text("Country") },
                            trailingIcon = {
                                Icon(Icons.Default.ArrowDropDown, "Select Country")
                            }
                        )
                    }
                }
                
                // Product Info Preview
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "📝 Product Details",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        OutlinedTextField(
                            value = "Labubu Halloween Keychain",
                            onValueChange = {},
                            label = { Text("Product Name") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        
                        OutlinedTextField(
                            value = "Limited Edition Collectible for Halloween 2024",
                            onValueChange = {},
                            label = { Text("Description") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3
                        )
                    }
                }
                
                // Pricing Preview
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "💰 Pricing (SGD)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        OutlinedTextField(
                            value = "22.90",
                            onValueChange = {},
                            label = { Text("Price") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Text("SGD", style = MaterialTheme.typography.bodyLarge) },
                            singleLine = true
                        )
                    }
                }
                
                // Official Store Preview
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "🏪 Official Store",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        OutlinedTextField(
                            value = "https://www.popmart.com/products/labubu-halloween",
                            onValueChange = {},
                            label = { Text("Official Store URL") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            leadingIcon = {
                                Text("🔗", style = MaterialTheme.typography.bodyLarge)
                            }
                        )
                        
                        Text(
                            text = "Add the official store link where this product is originally sold",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                // Marketplaces Preview
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "🛒 Available Marketplaces",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        Text(
                            text = "Select where this product is available:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        // Shopee (selected)
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Checkbox(
                                    checked = true,
                                    onCheckedChange = null
                                )
                                
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Shopee",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Medium
                                    )
                                    
                                    OutlinedTextField(
                                        value = "https://shopee.sg/product/123",
                                        onValueChange = {},
                                        label = { Text("Product URL (optional)") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp),
                                        singleLine = true
                                    )
                                    
                                    OutlinedTextField(
                                        readOnly = true,
                                        value = "InStock",
                                        onValueChange = {},
                                        label = { Text("Availability") },
                                        trailingIcon = {
                                            Icon(Icons.Default.ArrowDropDown, "Dropdown")
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp)
                                    )
                                }
                            }
                        }
                        
                        // Lazada (unselected)
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Checkbox(
                                    checked = false,
                                    onCheckedChange = null
                                )
                                
                                Text(
                                    text = "Shopee",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        
                        // Lazada (unselected)
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Checkbox(
                                    checked = false,
                                    onCheckedChange = null
                                )
                                
                                Text(
                                    text = "Lazada",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}